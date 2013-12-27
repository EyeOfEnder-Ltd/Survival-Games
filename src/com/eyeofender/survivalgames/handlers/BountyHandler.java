package com.eyeofender.survivalgames.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class BountyHandler {

	private Player placed;
	private int amount;
	private Map<String, Integer> placers = new HashMap<String, Integer>();
	
	public BountyHandler ( Player placed, int amount ){
		this.setPlaced(placed);
		this.setAmount(amount);
	}

	public Player getPlaced() {
		return placed;
	}

	public void setPlaced(Player placed) {
		this.placed = placed;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Map<String, Integer> getPlacers() {
		return placers;
	}
}
