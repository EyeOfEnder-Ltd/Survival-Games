package com.eyeofender.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.eyeofender.survivalgames.SurvivalGames;

public class EntityListener implements Listener {
	
	SurvivalGames plugin;
	
	public EntityListener ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
    	event.setCancelled(true);
    }
}
