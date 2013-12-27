package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class WaitTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	int timer;
	
	public WaitTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getWaitTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			for(Player player : Bukkit.getOnlinePlayers()){
				player.setLevel(timer);
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer <= 5){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Teleporting players to map in: " + ChatColor.GOLD + timer + " seconds.");
			}
		}
		
		if(timer == 0){
			plugin.getServer().getScheduler().cancelTask(t);
			plugin.getGm().loadGame();
		}
	}
}
