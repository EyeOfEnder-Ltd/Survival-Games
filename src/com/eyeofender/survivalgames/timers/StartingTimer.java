package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class StartingTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	int timer;
	
	public StartingTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getStartingTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			plugin.getGame().getWorld().setMonsterSpawnLimit(0);
		}
		
		if(timer <= 5){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Game starting in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.playSound(player.getLocation(), Sound.ENDERMAN_DEATH, 10, 10);
			}
			plugin.getServer().getScheduler().cancelTask(t);
			plugin.getGm().startGame();
		}
	}
}
