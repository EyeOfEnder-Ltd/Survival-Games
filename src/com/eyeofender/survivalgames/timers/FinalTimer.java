package com.eyeofender.survivalgames.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.eyeofender.survivalgames.SurvivalGames;

public class FinalTimer implements Listener, Runnable {

	SurvivalGames plugin;
	
	public int t;
	int timer;
	
	public FinalTimer ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		timer = plugin.getConfigHandler().getDeathmatchTimer();
		
		t = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
	}

	@Override
	public void run() {
		if(timer != 0){
			timer--;
			
			for(Player p : Bukkit.getOnlinePlayers()){
			        Location loc = p.getLocation();
			        Location sLoc = plugin.getGame().getSpawns().get(0);
			        Location tpTo = loc.clone();
			        int fromSpawn = loc.getBlockX() - sLoc.getBlockX();
			        int borderSize = plugin.getConfigHandler().getDeathmatchBordersize();
			        int from = borderSize - (borderSize / 25);
			        if (fromSpawn > from) {
			            tpTo.setX(((borderSize - 2) + sLoc.getBlockX()));
			        }
			        if (fromSpawn < -(from)) {
			            tpTo.setX((-(borderSize - 2) + sLoc.getBlockX()));
			        }
			        boolean hurt = Math.abs(fromSpawn) >= borderSize;
			        fromSpawn = loc.getBlockZ() - sLoc.getBlockZ();
			        if (fromSpawn > (from)) {
			            tpTo.setZ(((borderSize - 2) + sLoc.getBlockZ()));
			        }
			        if (fromSpawn < -(from)) {
			            tpTo.setZ((-(borderSize - 2) + sLoc.getBlockZ()));
			        }
			        if (!hurt) hurt = Math.abs(fromSpawn) >= borderSize;
			        if (!loc.equals(tpTo) && plugin.getGm().getIsAlive().contains(p)) {
			            if (hurt) p.sendMessage(ChatColor.RED + "Haul your ass back into spawn maggot!");
			            else
			                p.sendMessage(ChatColor.RED + "Exploring is not permitted! Turn away or be shot!");
			        }
			        if (hurt) {
			            if (plugin.getGm().getIsAlive().contains(p)) {
			                if (p.getHealth() - 2 > 0) {
			                    p.damage(0);
			                    p.setHealth(p.getHealth() - 2);
			                } else {
			                	p.damage(4);
			                	plugin.getGm().removePlayer(p);
			                	if(plugin.getGm().getIsAlive().contains(p))
			                		plugin.getGm().getIsAlive().remove(p);
			                	plugin.getGm().checkPlayers();
			                }
			            } 
			        }
			    }
			}
		
		if(timer % 60 == 0 && timer >= 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Deathmatch ending in: " +ChatColor.GOLD + timer/60 + " minutes.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer % 15 == 0 && timer >= 10 && timer < 60){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Deathmatch ending in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer % 1 == 0 && timer >= 0 && timer <= 10){
			for(Player player : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(player, "Deathmatch ending in: " + ChatColor.GOLD + timer + " seconds.");
				player.playSound(player.getLocation(), Sound.CLICK, 10, 10);
			}
		}
		
		if(timer == 0){
			plugin.getGm().findWinner();
		}
	}
}
