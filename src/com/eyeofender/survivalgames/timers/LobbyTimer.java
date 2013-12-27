package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class LobbyTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	public int timer;
	
	public LobbyTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getLobbyTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			for(Player player : Bukkit.getOnlinePlayers()){
				player.setLevel(timer);
			}
			
			if (Bukkit.getOnlinePlayers().length >= (plugin.getConfigHandler().getMaxPlayers() * .50) && timer >= 120) timer = 120;
            
			if (Bukkit.getOnlinePlayers().length >= (plugin.getConfigHandler().getMaxPlayers() * .75) && timer >= 60) timer = 60;
			
			if (Bukkit.getOnlinePlayers().length >= (plugin.getConfigHandler().getMaxPlayers()) && timer >= 30) timer = 30;
		}
		
		if(timer % 60 == 0 && timer >= 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Voting ending in: " +ChatColor.GOLD + timer/60 + " minutes.");
			}
		}
		
		if(timer % 30 == 0 && timer % 60 != 0){
			plugin.getVh().listMaps();
		}
		
		if(timer % 15 == 0 && timer >= 10 && timer < 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Voting ending in: " + ChatColor.GOLD + timer + " seconds.");
			}
		}
		
		if(timer % 1 == 0 && timer >= 0 && timer <= 10){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Voting ending in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			if(plugin.getConfigHandler().getMinPlayers() > plugin.getServer().getOnlinePlayers().length){
				for(Player player : plugin.getServer().getOnlinePlayers()){
					plugin.sendMessage(player, "Not have enough players to start.");
				}
				
				this.timer = plugin.getConfigHandler().getLobbyTimer();
				
				return;
			}
			
			plugin.getServer().getScheduler().cancelTask(t);
			plugin.getGm().loadArena();
		}
	}
}
