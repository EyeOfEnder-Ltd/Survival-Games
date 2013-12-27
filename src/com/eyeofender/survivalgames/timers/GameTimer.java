package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.util.Kit;

public class GameTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	public int timer;
	
	public GameTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getGameTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			plugin.getRc().checkRessurrected();
		}
		
		if(plugin.getConfigHandler().getGameTimer() - 90 == timer){
		    System.out.print("Now doing the kits spreading");
		    int cur = 1;
		    for (final Kit kit : plugin.getKm().getKits()) {
		      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		        public void run() {
		          kit.giveKit();
		        }
		      }
		      , cur);
		      cur += (int)Math.floor(kit.getAmount() / 10);
		    }
		    for(Player player : Bukkit.getOnlinePlayers()){
		    	plugin.sendMessage(player, "Kits have been spread to all players that picked one.");
		    }
		    System.out.print("Kit speading done");
		}
		
		if(timer % 60 == 0 && timer >= 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Teleporting to deathmatch in: " +ChatColor.GOLD + timer/60 + " minutes.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer % 15 == 0 && timer >= 10 && timer < 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Teleporting to deathmatch in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer % 1 == 0 && timer >= 0 && timer <= 10){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Teleporting to deathmatch in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			plugin.getGm().loadDeathmatch();
		}
	}
}
