package com.eyeofender.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.eyeofender.survivalgames.SurvivalGames;

public class ServerListener implements Listener {

	SurvivalGames plugin;
	
	public ServerListener(SurvivalGames plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onServerListPing (ServerListPingEvent event){
		if(!plugin.getGm().isInGame()){
			if(plugin.getConfigHandler().getMaxPlayers() >= Bukkit.getOnlinePlayers().length){
				if(plugin.getLobbyTimer().timer >= 60){
						event.setMotd(ChatColor.GREEN  + "Waiting : " +ChatColor.GOLD + plugin.getLobbyTimer().timer/60 + " m");
				}
				
				if(plugin.getLobbyTimer().timer < 60){
						event.setMotd(ChatColor.YELLOW + "Starting : " + ChatColor.GOLD + plugin.getLobbyTimer().timer + " s");
				}
			}else{
				event.setMotd(ChatColor.RED + "Full");
			}
		}else{
			event.setMotd(ChatColor.RED + "In Progress");
		}
	}

}
