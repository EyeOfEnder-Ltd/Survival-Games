package com.eyeofender.survivalgames.game;

import org.bukkit.entity.Player;

public class GamePlayer {
	
	/** Player Stats **/
	private String name;
	private Player player;
	private Player killer;
	private String kit;
	private int kills;
	
	/** Classes **/
	GameManager gm;
	
	public GamePlayer ( Player player , GameManager gm){
		this.setName(player.getName());
		this.setPlayer(player);
		this.gm = gm;
	}
	
	
	/**********************************************
	 * 
	 * 				Getter & Setter
	 * 
	 *********************************************/
	
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getKit() {
		return kit;
	}


	public void setKit(String kit) {
		this.kit = kit;
	}


	public Player getKiller() {
		return killer;
	}


	public void setKiller(Player killer) {
		this.killer = killer;
	}
	
}
