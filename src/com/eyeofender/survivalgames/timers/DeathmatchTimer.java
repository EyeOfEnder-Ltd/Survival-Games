package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class DeathmatchTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	int timer;
	
	public DeathmatchTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = 10;
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
		}
		
		if(timer % 1 == 0 && timer >= 0 && timer <= 10){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Deathmatch starting in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			plugin.getGm().startDeathmatch();
		}
	}
}
