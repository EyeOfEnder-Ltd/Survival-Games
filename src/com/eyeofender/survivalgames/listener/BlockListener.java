package com.eyeofender.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.eyeofender.survivalgames.SurvivalGames;

public class BlockListener implements Listener {

	SurvivalGames plugin;
	
	public BlockListener ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace ( BlockPlaceEvent event ){
		if(plugin.getBuild().contains(event.getPlayer())){
			return;
		}
		
		if(plugin.getConfigHandler().getWhitelistedBlocks().contains(event.getBlock().getType().getId())){
			return;
		}
		
		event.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak ( BlockBreakEvent event ){
		if(plugin.getBuild().contains(event.getPlayer())){
			return;
		}
		
		if(plugin.getConfigHandler().getWhitelistedBlocks().contains(event.getBlock().getType().getId())){
			return;
		}
		
		event.setCancelled(true);
	}
}
