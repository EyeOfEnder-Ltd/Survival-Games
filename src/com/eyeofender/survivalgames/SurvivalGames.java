package com.eyeofender.survivalgames;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

import com.eyeofender.enderpearl.EnderPearl;
import com.eyeofender.survivalgames.game.Game;
import com.eyeofender.survivalgames.game.GameCreator;
import com.eyeofender.survivalgames.game.GameManager;
import com.eyeofender.survivalgames.handlers.BountyHandler;
import com.eyeofender.survivalgames.handlers.CommandHandler;
import com.eyeofender.survivalgames.handlers.ConfigurationHandler;
import com.eyeofender.survivalgames.handlers.FileHandler;
import com.eyeofender.survivalgames.handlers.KitHandler;
import com.eyeofender.survivalgames.handlers.ResurrectionHandler;
import com.eyeofender.survivalgames.handlers.SponsorHandler;
import com.eyeofender.survivalgames.handlers.VoteHandler;
import com.eyeofender.survivalgames.handlers.WorldHandler;
import com.eyeofender.survivalgames.listener.BlockListener;
import com.eyeofender.survivalgames.listener.BountyListener;
import com.eyeofender.survivalgames.listener.ChestListener;
import com.eyeofender.survivalgames.listener.EntityListener;
import com.eyeofender.survivalgames.listener.PlayerListener;
import com.eyeofender.survivalgames.listener.ServerListener;
import com.eyeofender.survivalgames.menus.Menu;
import com.eyeofender.survivalgames.models.StatsSystem;
import com.eyeofender.survivalgames.timers.LobbyTimer;

public class SurvivalGames extends JavaPlugin{
	
	/** Classes **/
	private ConfigurationHandler configHandler = new ConfigurationHandler(this);
	private ResurrectionHandler rc = new ResurrectionHandler(this);
	private CommandHandler ch = new CommandHandler(this);
	private SponsorHandler sh = new SponsorHandler(this);
	private WorldHandler wh = new WorldHandler(this);
	private StatsSystem ss = new StatsSystem(this);
	private FileHandler fh = new FileHandler(this);
	private GameCreator gc = new GameCreator(this);
	private GameManager gm = new GameManager(this);
	private VoteHandler vh = new VoteHandler(this);
	private KitHandler km = new KitHandler(this);
	private EnderPearl ep;
	private DisguiseCraftAPI dcAPI;
	private Menu m = new Menu(this);

	/** Game Classes **/
	private Game game = new Game(this);
	
	/** Timers **/
	private LobbyTimer lobbyTimer = new LobbyTimer(this);
	
	/** Outported Classes **/
	
	
	/** Public lists **/
	private List<BountyHandler> bounties = new ArrayList<BountyHandler>();
	private List<Player> frozen = new ArrayList<Player>();
	private List<Player> muted = new ArrayList<Player>();
	private List<Player> build = new ArrayList<Player>();
	
	@Override
	public void onEnable() 
	{
		/** Inits **/
		this.configHandler.init();
		this.fh.init();
		this.ch.init();
		this.vh.init();
		this.km.init();
		this.ss.init();
		m.loadListeners();
		this.setupDisguiseCraft();
		
		/** Loads lobby **/
		this.game.loadLobby();
		
		/** Enabling Listeners **/
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new EntityListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new ChestListener(this), this);
		pm.registerEvents(new ServerListener(this), this);
		pm.registerEvents(new BountyListener(this), this);
		
		/** Adding bungeecord **/
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        /** Starting lobby Timer **/
        LobbyTimer lt = new LobbyTimer(this);
        lt.init();
        
        try {
            this.ep = (EnderPearl) pm.getPlugin("EnderPearl");
        } catch (NoClassDefFoundError e) {
            getLogger().info("Unsupported or no version of EnderPearl found.");
            pm.disablePlugin(this);
            return;
        }
	}

	@Override
	public void onDisable() 
	{
		this.ss.exit();
	}
	
	/**********************************************
	 * 
	 * 				Messaging
	 * 
	 *********************************************/
	
	public void sendMessage(Player player, String message){
		player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "< " + ChatColor.GOLD + "SG" + ChatColor.RED + ChatColor.BOLD + " > " + ChatColor.GRAY + message);
	}
	
	/**********************************************
	 * 
	 * 				Helper Methods
	 * 
	 *********************************************/

    public void sendPlayer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException eee) {
        	player.kickPlayer("Error has occured while sending you to the hub.");
        }
        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }
	
	/**********************************************
	 * 
	 * 				Getter & Setter
	 * 
	 *********************************************/

	public GameManager getGm() {
		return gm;
	}

	public void setGm(GameManager gm) {
		this.gm = gm;
	}
	
	public ConfigurationHandler getConfigHandler() {
		return configHandler;
	}

	public void setConfigHandler(ConfigurationHandler configHandler) {
		this.configHandler = configHandler;
	}
	
	public FileHandler getFh() {
		return fh;
	}

	public void setFh(FileHandler fh) {
		this.fh = fh;
	}

	public GameCreator getGc() {
		return gc;
	}

	public void setGc(GameCreator gc) {
		this.gc = gc;
	}

	public LobbyTimer getLobbyTimer() {
		return lobbyTimer;
	}

	public void setLobbyTimer(LobbyTimer lobbyTimer) {
		this.lobbyTimer = lobbyTimer;
	}

	public VoteHandler getVh() {
		return vh;
	}

	public void setVh(VoteHandler vh) {
		this.vh = vh;
	}

	public WorldHandler getWh() {
		return wh;
	}

	public void setWh(WorldHandler wh) {
		this.wh = wh;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Player> getFrozen() {
		return frozen;
	}

	public void setFrozen(List<Player> frozen) {
		this.frozen = frozen;
	}

	public List<Player> getMuted() {
		return muted;
	}

	public void setMuted(List<Player> muted) {
		this.muted = muted;
	}
	
	public KitHandler getKm() {
		return km;
	}

	public void setKm(KitHandler km) {
		this.km = km;
	}
	
	public StatsSystem getSs() {
		return ss;
	}

	public void setSs(StatsSystem ss) {
		this.ss = ss;
	}

	public void setupDisguiseCraft() {
		setDcAPI(DisguiseCraft.getAPI());
	}

	public DisguiseCraftAPI getDcAPI() {
		return dcAPI;
	}

	public void setDcAPI(DisguiseCraftAPI dcAPI) {
		this.dcAPI = dcAPI;
	}

	public ResurrectionHandler getRc() {
		return rc;
	}

	public void setRc(ResurrectionHandler rc) {
		this.rc = rc;
	}

	public List<BountyHandler> getBounties() {
		return bounties;
	}

	public void setBounties(List<BountyHandler> bounties) {
		this.bounties = bounties;
	}

	public SponsorHandler getSh() {
		return sh;
	}

	public void setSh(SponsorHandler sh) {
		this.sh = sh;
	}

	public EnderPearl getEp() {
		return ep;
	}

	public void setEp(EnderPearl ep) {
		this.ep = ep;
	}

	public List<Player> getBuild() {
		return build;
	}

	public void setBuild(List<Player> build) {
		this.build = build;
	}

	/**public RankManager getRankManager() {
		return rankManager;
	}

	public void setRankManager(RankManager rankManager) {
		this.rankManager = rankManager;
	}**/
}
