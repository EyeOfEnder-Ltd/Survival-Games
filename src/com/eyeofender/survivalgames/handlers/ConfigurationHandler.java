package com.eyeofender.survivalgames.handlers;

import java.util.ArrayList;
import java.util.List;

import com.eyeofender.survivalgames.SurvivalGames;

public class ConfigurationHandler {

	private int maxPlayers;
	private int minPlayers = 6;
	private int lobbyTimer = 300;
	private int waitTimer = 5;
	private int gameTimer = 900;
	private int startingTimer = 30;
	private int deathmatchPlayers = 6;
	private int deathmatchTimer = 150;
	private int deathmatchBordersize = 100;
	private List<Integer> whitelistedBlocks = new ArrayList<Integer>();
	/** Classes **/
	SurvivalGames plugin;
	
	public ConfigurationHandler ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		this.setMaxPlayers(plugin.getServer().getMaxPlayers());
		
		if(plugin.getConfig().contains("lobbyTimer"))
			this.setLobbyTimer(plugin.getConfig().getInt("lobbyTimer"));
		if(plugin.getConfig().contains("minPlayers"))
			this.setMinPlayers(plugin.getConfig().getInt("minPlayers"));
		if(plugin.getConfig().contains("gameTimer"))
			this.setGameTimer(plugin.getConfig().getInt("gameTimer"));
		if(plugin.getConfig().contains("waitTimer"))
			this.setWaitTimer(plugin.getConfig().getInt("waitTimer"));
		if(plugin.getConfig().contains("startingTimer"))
			this.setStartingTimer(plugin.getConfig().getInt("startingTimer"));
		if(plugin.getConfig().contains("deathmatchPlayers"))
			this.setDeathmatchPlayers(plugin.getConfig().getInt("deathmatchPlayers"));
		if(plugin.getConfig().contains("deathmatchTimer"))
			this.setDeathmatchTimer(plugin.getConfig().getInt("deathmatchTimer"));
		if(plugin.getConfig().contains("whitelistedBlocks"))
			this.setWhitelistedBlocks(plugin.getConfig().getIntegerList("whitelistedBlocks"));
		if(plugin.getConfig().contains("deathmatchBordersize"))
			this.setDeathmatchBordersize(plugin.getConfig().getInt("deathmatchBordersize"));
	}

	/**********************************************
	 * 
	 * 				Getter & Setter
	 * 
	 *********************************************/
	
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getLobbyTimer() {
		return lobbyTimer;
	}

	public void setLobbyTimer(int lobbyTimer) {
		this.lobbyTimer = lobbyTimer;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getGameTimer() {
		return gameTimer;
	}

	public void setGameTimer(int gameTimer) {
		this.gameTimer = gameTimer;
	}

	public int getWaitTimer() {
		return waitTimer;
	}

	public void setWaitTimer(int waitTimer) {
		this.waitTimer = waitTimer;
	}

	public int getStartingTimer() {
		return startingTimer;
	}

	public void setStartingTimer(int startingTimer) {
		this.startingTimer = startingTimer;
	}

	public int getDeathmatchPlayers() {
		return deathmatchPlayers;
	}

	public void setDeathmatchPlayers(int deathmatchPlayers) {
		this.deathmatchPlayers = deathmatchPlayers;
	}

	public int getDeathmatchTimer() {
		return deathmatchTimer;
	}

	public void setDeathmatchTimer(int deathmatchTimer) {
		this.deathmatchTimer = deathmatchTimer;
	}

	public List<Integer> getWhitelistedBlocks() {
		return whitelistedBlocks;
	}

	public void setWhitelistedBlocks(List<Integer> whitelistedBlocks) {
		this.whitelistedBlocks = whitelistedBlocks;
	}

	public int getDeathmatchBordersize() {
		return deathmatchBordersize;
	}

	public void setDeathmatchBordersize(int deathmatchBordersize) {
		this.deathmatchBordersize = deathmatchBordersize;
	}
}
