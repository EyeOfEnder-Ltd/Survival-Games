package com.eyeofender.survivalgames.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.eyeofender.UniversalCredits.UniversalCredits;
import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.events.GameStartEvent;
import com.eyeofender.survivalgames.events.PlayerWinEvent;
import com.eyeofender.survivalgames.models.Stats;
import com.eyeofender.survivalgames.timers.DeathmatchTimer;
import com.eyeofender.survivalgames.timers.EndTimer;
import com.eyeofender.survivalgames.timers.FinalTimer;
import com.eyeofender.survivalgames.timers.GameTimer;
import com.eyeofender.survivalgames.timers.StartingTimer;
import com.eyeofender.survivalgames.timers.WaitTimer;

public class GameManager {

	/** Players **/
	private List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>();
	private List<Player> isAlive = new ArrayList<Player>();
	private List<Player> spec = new ArrayList<Player>();
	private List<Player> usedPass = new ArrayList<Player>();
	private List<Player> resurrected = new ArrayList<Player>();
	private List<Player> rampage = new ArrayList<Player>();
	
	/** Game Stuff **/
	private boolean inGame; 
	private boolean inDeathmatch = false;
	
	/** Classes **/
	SurvivalGames plugin;
	GameTimer gt;
	DeathmatchTimer dmt;
	FinalTimer ft;
	
	/** Constructor **/
	public GameManager ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	/** Run when a player joins the arena **/
	public void joinArena(Player player){
		if(inGame) {
			addSpectator(player); 
			return;
		}
		
		GamePlayer gp;
		
		if(findGamePlayer(player) == null){
			gp = new GamePlayer(player, this);
			gamePlayers.add(gp);
		}
		
		gp = this.findGamePlayer(player);

        /** Updates Players Inventory **/
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1F);
        player.setFlying(false);
        player.setExp(0);
        player.getLocation().getWorld().setStorm(false);
        player.getLocation().getWorld().setTime(0);
        
        /** Clears Player Items | Potion Effects **/
        this.clearInventory(player);
        this.clearPotionEffects(player);
        
        
        /** Teleports player to lobby **/
        player.teleport(plugin.getGame().getLobby().clone().add(0.5D, 0.5D, 0.5D));
	}
	
	public void loadArena(){
		this.inGame = true;

		plugin.getVh().loadMap();
		
		WaitTimer wt = new WaitTimer(plugin);
		wt.init();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getMuted().add(player);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Game is loading");
			player.sendMessage("");
			plugin.sendMessage(player, "Current Map " + plugin.getVh().getMap() + "!");
			player.sendMessage("");
			plugin.sendMessage(player, "Chat has been muted.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			//Mute chat until game countdown starts
		}
	}
	
	public void loadGame(){
		plugin.getServer().getScheduler().cancelTask(plugin.getLobbyTimer().t);
		/** Spawn all players **/
		plugin.getFh().load();
		plugin.getGame().loadSpawns();
		plugin.getGame().getWorld().setTime(0);
		plugin.getGame().getWorld().setStorm(false);
		plugin.getGame().getWorld().setSpawnFlags(false, false);
		for(Entity e : plugin.getGame().getWorld().getEntities()){
            e.remove();
        }
		
		List<Player> players = new ArrayList<Player>(); 
		
		for(Player player : Bukkit.getOnlinePlayers()){
			players.add(player);
		}
		
		int i = 0;
		
		for(Player player: Bukkit.getOnlinePlayers()){
			try{
				player.teleport(plugin.getGame().getSpawns().get(i));
				isAlive.add(player);
				i++;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getFrozen().add(player);
			plugin.getMuted().remove(player);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Game is starting in " + plugin.getConfigHandler().getStartingTimer() + " seconds!");
			player.sendMessage("");
			plugin.sendMessage(player, "Players have been frozen.");
			player.sendMessage("");
			plugin.sendMessage(player, "Chat has been unmuted.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			//Mute chat until game countdown starts
		}
		
		StartingTimer st = new StartingTimer(plugin);
		st.init();
	}
	
	public void startGame(){
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getFrozen().remove(player);
	        player.setExp(0);
	        player.setLevel(0);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"The games have started!");
			player.sendMessage("");
			plugin.sendMessage(player, "Players have been unfrozen.");
			player.sendMessage("");
			plugin.sendMessage(player, "May the odds be in your favor.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
		
		/** Loads the event **/
        GameStartEvent event = new GameStartEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
		
		GameTimer gt = new GameTimer(plugin);
		gt.init();
		this.gt = gt;
		
		this.checkDeathmatch();
	}
	
	public void checkDeathmatch(){
		if(!isInDeathmatch()){
			if(this.getIsAlive().size() <= plugin.getConfigHandler().getDeathmatchPlayers()){
				this.gt.timer = 60;
				this.inDeathmatch = true;
			}
		}
	}
	
	public void checkPlayers(){
		if(this.getIsAlive().size() == 1){
			this.findWinner();
		}
	}
	
	public void loadDeathmatch(){
		plugin.getServer().getScheduler().cancelTask(gt.t);
		
		DeathmatchTimer dmt = new DeathmatchTimer(plugin);
		dmt.init();
		this.dmt = dmt;
		
		List<Player> players = new ArrayList<Player>(); 
		
		for(Player player : this.isAlive){
			players.add(player);
		}

		int i = 0;
		for(Player player : getIsAlive()){
			player.teleport(plugin.getGame().getDmspawns().get(i));
			plugin.getFrozen().add(player);
			plugin.getMuted().add(player);
			i++;
		}
		
		for(Player player : Bukkit.getOnlinePlayers()){
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"Chat has been muted!");
			player.sendMessage("");
			plugin.sendMessage(player, "There a " + this.getIsAlive().size() + " players in the deathmatch!");
			player.sendMessage("");
			plugin.sendMessage(player, "All alive players have been frozen!");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
	}
	
	public void startDeathmatch(){
		plugin.getServer().getScheduler().cancelAllTasks();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getFrozen().remove(player);
			plugin.getMuted().remove(player);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player ,"You have been unmuted!");
			player.sendMessage("");
			plugin.sendMessage(player, "Deathmatch lasts for " + plugin.getConfigHandler().getDeathmatchTimer() + " seconds!");
			player.sendMessage("");
			plugin.sendMessage(player, "All alive players have been unfrozen!");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
		
		FinalTimer ft = new FinalTimer(plugin);
		ft.init();
		this.ft = ft;
	}
	
	public void finishTie(){
		plugin.getServer().getScheduler().cancelAllTasks();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getFrozen().clear();
			plugin.getMuted().clear();
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player , "The game has been tied. Time ran out!");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
	}
	
	public void findWinner(){
		plugin.getServer().getScheduler().cancelAllTasks();
		
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.getFrozen().clear();
			plugin.getMuted().clear();
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player , this.getIsAlive().get(0).getName() + " has won that game.");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
		
		/** Loads the event **/
		PlayerWinEvent event = new PlayerWinEvent(this.getIsAlive().get(0));
        Bukkit.getServer().getPluginManager().callEvent(event);
        
        UniversalCredits.api.pay(this.getIsAlive().get(0).getName(), 5, false);
        
        Player player = this.getIsAlive().get(0);

        if(player != null){
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player , "You have received 100 Credits for winning the game!");
			if(findGamePlayer(player).getKills() != 0){
				int kills = findGamePlayer(player).getKills();
				UniversalCredits.api.pay(player.getName(), kills * 5, false);
				player.sendMessage("");
				plugin.sendMessage(player , "You have received " + kills * 5 + " Credits for killing " + kills + " players!");
				if( player.isOp() || plugin.getEp().getRankManager().hasRank(player)){
					player.sendMessage("");
					plugin.sendMessage(player, "You have received a bonus of " + ((kills * 5) + 100) + " Credits for being a donator." );
				}
			}
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
        }

		EndTimer et = new EndTimer(plugin);
		et.init();
	}
	
	public void removePlayer(Player player){
		if(this.isAlive.contains(player))
			this.isAlive.remove(player);
		if(plugin.getFrozen().contains(player))
			plugin.getFrozen().remove(player);
		if(plugin.getMuted().contains(player))
			plugin.getMuted().remove(player);
		if(resurrected.contains(player))
			resurrected.remove(player);
		if(rampage.contains(player))
			rampage.remove(player);
		for(GamePlayer gp : this.getGamePlayers()){
			if(gp.getPlayer() == player){
				giveCredits(player);
				return;
			}
		}
	}
	
	public void giveCredits(Player player){
		if(findGamePlayer(player).getKills() != 0){
			int kills = findGamePlayer(player).getKills();
			UniversalCredits.api.pay(player.getName(), kills * 5, false);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
			player.sendMessage("");
			plugin.sendMessage(player , "You have received " + kills * 5 + " Credits for killing " + kills + " players!");
			if(player.isOp() || plugin.getEp().getRankManager().hasRank(player)){
				player.sendMessage("");
				plugin.sendMessage(player, "You have received a bonus of " + (kills * 5 ) + " Credits for being a donator." );
			}
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=- -=-");
		}
	}
	
	public void useResurrection(Player player){
        Stats stats = (Stats) plugin.getSs().getPlayerStats().get(player.getName());
        
        if(!getIsAlive().contains(player)){
        	if(!isInDeathmatch()){
        		if(isInGame()){
			        if(stats.getMutationPasses() > 0 || player.isOp()){
			        	if(!usedPass.contains(player)){
			        		if(findGamePlayer(player).getKiller() != null){
				        		usedPass.add(player);
				        		plugin.getRc().init(player);
			        		}
			        	}
			        }
        		}
        	}
        }
	}

	public void addSpectator(Player player){
		spec.add(player);
		
		player.setAllowFlight(true);
        player.setFlySpeed(0.1F);
        player.setFlying(false);
        
        for(Player players : Bukkit.getOnlinePlayers()){
        	players.hidePlayer(player);
        }
        
        for(Player players : spec){
        	players.hidePlayer(player);
        }
        
        player.teleport(plugin.getGame().getLobby().clone().add(0.5D, 250, 0.5D));
	}
	
	 public GamePlayer findGamePlayer(Player player) {
		 for (GamePlayer ap : this.getGamePlayers()) {
	            if (ap.getName().equalsIgnoreCase(player.getName())) {
	                return ap;
	            }
		 }

	        return null;
	 }
	 
	 public void clearPotionEffects(Player player) {
	        for (PotionEffect effect : player.getActivePotionEffects()) {
	            player.removePotionEffect(effect.getType());
	        }
	    }

	    public void clearInventory(Player player) {
	        player.getInventory().clear();

	        player.getInventory().setHelmet(new ItemStack(Material.AIR));
	        player.getInventory().setChestplate(new ItemStack(Material.AIR));
	        player.getInventory().setLeggings(new ItemStack(Material.AIR));
	        player.getInventory().setBoots(new ItemStack(Material.AIR));
	    }
	 
	/**********************************************
	 * 
	 * 				Getter & Setter
	 * 
	 *********************************************/
	
	public List<Player> getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(List<Player> isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public List<GamePlayer> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public boolean isInDeathmatch() {
		return inDeathmatch;
	}

	public void setInDeathmatch(boolean inDeathmatch) {
		this.inDeathmatch = inDeathmatch;
	}

	public List<Player> getUsedPass() {
		return usedPass;
	}

	public void setUsedPass(List<Player> usedPass) {
		this.usedPass = usedPass;
	}

	public List<Player> getResurrected() {
		return resurrected;
	}

	public void setResurrected(List<Player> resurrected) {
		this.resurrected = resurrected;
	}

	public List<Player> getRampage() {
		return rampage;
	}

	public void setRampage(List<Player> rampage) {
		this.rampage = rampage;
	}
}
