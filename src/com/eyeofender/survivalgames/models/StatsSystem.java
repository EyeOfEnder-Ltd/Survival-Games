package com.eyeofender.survivalgames.models;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.events.GameStartEvent;
import com.eyeofender.survivalgames.events.PlayerKilledEvent;
import com.eyeofender.survivalgames.events.PlayerWinEvent;
import com.eyeofender.survivalgames.handlers.KitHandler;
import com.eyeofender.survivalgames.util.Kit;

public class StatsSystem implements Listener {
    public ConcurrentHashMap<String, Stats> playerStats = new ConcurrentHashMap<String, Stats>();
    public String SQL_USER;
    public String SQL_PASS;
    public String SQL_DATA;
    public String SQL_HOST;
    private StatsSaveThread saveThread;
    private StatsLoadThread loadThread;
    private SurvivalGames plugin;

	private KitHandler kits = new KitHandler(plugin);
    private Config config;

    public StatsSystem ( SurvivalGames plugin ){
    	this.plugin = plugin;
    }
    
    public void init() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(this, plugin);
        config = new Config(this);
        SQL_USER = config.getUsername();
        SQL_PASS = config.getPassword();
        SQL_HOST = config.getHost();
        SQL_DATA = config.getDatabase();
        this.loadThread = new StatsLoadThread(this);
        this.saveThread = new StatsSaveThread(this);
        this.loadThread.start();
        this.saveThread.start();
    }

    public Stats getStats(String name) {
        return (Stats) this.getPlayerStats().get(name);
    }

    public void exit() {
        for (Player p : Bukkit.getOnlinePlayers())
            p.kickPlayer("Bye bye!, Game shutdown for weird reasons!");
        while (this.loadThread.joinQueue.size() > 0) {
            try {
                System.out.println("Waiting for stats join queue to finish");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Stats stats : this.getPlayerStats().values())
            this.saveThread.quitQueue.add(stats);
        this.getPlayerStats().clear();
        while (this.saveThread.quitQueue.size() > 0) {
            try {
                System.out.println("Waiting for stats quit queue to finish");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.loadThread.terminate();
        this.saveThread.terminate();
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        this.loadThread.joinQueue.add(event.getPlayer().getName());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Stats stats = (Stats) this.getPlayerStats().remove(event.getPlayer().getName());
        if (stats != null) this.saveThread.quitQueue.add(stats);
    }

    @EventHandler
    public void onDeath(PlayerKilledEvent event) {
        if (event.getKiller() != null) {
            Stats stats = (Stats) this.getPlayerStats().get(event.getKilled().getName());
            if (stats != null) {
                stats.addKill();
                stats.setKills(stats.getKills() + 1);
                if (stats.getCurrentKills() % 5 == 0) event.getKiller().sendMessage(ChatColor.RED + "Kill streak of " + stats.getCurrentKills() + "!");
                if (stats.getCurrentKills() > stats.getHighestKillStreak()) {
                    event.getKiller().sendMessage(ChatColor.RED + "You just broke your previous killstreak record! " + stats.getCurrentKills() + " kills!");
                    stats.setHighestKillStreak(stats.getCurrentKills());
                }
            }
            Stats dead = (Stats) this.getPlayerStats().get(event.getKiller().getName());
            dead.setDeaths(dead.getDeaths() + 1);
        }
    }

    @EventHandler
    public void onWin(PlayerWinEvent event) {
        Stats stats = (Stats) this.getPlayerStats().get(event.getWinner().getName());
        if (stats != null) stats.setWins(stats.getWins() + 1);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Stats stats = (Stats) this.getPlayerStats().get(p.getName());
            if (stats != null) stats.addTimesPlayed();
        }
    }
    
    public void loadedStats(Stats stats) {
        if (stats.getSavedKit() == null){
        	 boolean loaded = true; 
        	 this.kits.getKitByName(plugin.getConfig().getString("defaultKit")).addPlayer(stats.getName());
        	 if (loaded) Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "Loaded saved kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
             else{
                 Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "Failed to load saved kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
             }
        } else{
        	if (this.kits.hasKit(Bukkit.getPlayerExact(stats.getName()), this.kits.getKitByName(stats.getSavedKit()).getName())) {
                boolean loaded = true;
                this.kits.getKitByName(stats.getSavedKit()).addPlayer(stats.getName());
                if (loaded) Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "Loaded saved kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
                else{
                    Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "Failed to load saved kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
                }
               } else {
                Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "No permission to use kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
                Bukkit.getPlayerExact(stats.getName()).sendMessage(ChatColor.RED + "Removed saved kit: " + this.kits.getKitByName(stats.getSavedKit()).getName());
            }
        }
    }
    
    public void addPermission(String player, String permissionNode)
    {
        ru.tehkode.permissions.bukkit.PermissionsEx.getUser(player).addPermission(permissionNode);
    }
     
    public void removePermission(String player, String permissionNode)
    {
        ru.tehkode.permissions.bukkit.PermissionsEx.getUser(player).removePermission(permissionNode);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Stats stats = (Stats) this.getPlayerStats().get(sender.getName());
        if (stats == null) {
            sender.sendMessage(ChatColor.RED + "Your stats have not loaded yet");
            return true;
        }
        Player p = Bukkit.getPlayerExact(sender.getName());
        if (cmd.getName().equalsIgnoreCase("savekit")) if (args.length > 0) {
        	Kit kit = this.kits.getKitByName(args[0]);
            if (kit == null) {
                p.sendMessage(ChatColor.RED + "That kit does not exist!");
                return true;
            }
            if (this.kits.hasKit(p, kit.getName())) {
                stats.setSavedKit(kit.getSafeName());
                p.sendMessage(ChatColor.RED + "Saved kit " + kit.getName() + ChatColor.RESET + ChatColor.RED + "!");
            } else {
                p.sendMessage(ChatColor.RED + "You do not own that kit!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must define a kit name");
        }

        if (cmd.getName().equalsIgnoreCase("stats")) {
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "That player does not exist");
                    return true;
                }
                stats = (Stats) this.getPlayerStats().get(player.getName());
                if (stats == null) p.sendMessage(ChatColor.RED + player.getName() + "'s stats has not loaded yet");
            }
            stats.endTimeLog();
            stats.startTimeLog();
            sender.sendMessage(ChatColor.DARK_AQUA + "-- Now displaying " + stats.getName() + " stats --");
            sender.sendMessage(ChatColor.BLUE + "Wins: " + ChatColor.AQUA + stats.getWins());
            sender.sendMessage(ChatColor.BLUE + "Losses: " + ChatColor.AQUA + stats.getLosses());
            sender.sendMessage(ChatColor.BLUE + "Games played: " + ChatColor.AQUA + (stats.getWins() + stats.getLosses()));
            sender.sendMessage(ChatColor.BLUE + "Total kills: " + ChatColor.AQUA + stats.getKills());
            sender.sendMessage(ChatColor.BLUE + "Total death: " + ChatColor.AQUA + stats.getDeaths());
            sender.sendMessage(ChatColor.BLUE + "Best kill streak: " + ChatColor.AQUA + stats.getHighestKillStreak());
            sender.sendMessage(ChatColor.BLUE + "Current kill streak: " + ChatColor.AQUA + stats.getCurrentKills());
            sender.sendMessage(ChatColor.BLUE + "Time logged: " + ChatColor.AQUA + stats.getTime(stats.getLogged()));
            sender.sendMessage(ChatColor.DARK_AQUA + "-- End of " + stats.getName() + "'s stats --");
        }
        return true;
    }
    
    public SurvivalGames getPlugin() {
		return plugin;
	}

	public void setPlugin(SurvivalGames plugin) {
		this.plugin = plugin;
	}

	public ConcurrentHashMap<String, Stats> getPlayerStats() {
		return playerStats;
	}

	public void setPlayerStats(ConcurrentHashMap<String, Stats> playerStats) {
		this.playerStats = playerStats;
	}
}