package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class EndTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	int timer;
	
	public EndTimer ( SurvivalGames plugin ){
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
		
		if(timer == 0){
			try{
				for(Player player : Bukkit.getOnlinePlayers()){
					player.kickPlayer("I love cats.");
				}
			}catch(Exception ex){
				for(Player player : Bukkit.getOnlinePlayers()){
					player.kickPlayer("I love cats.");
				}
			}finally{
				Bukkit.shutdown();
			}
		}
	}
}