package com.eyeofender.survivalgames.listener;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.eyeofender.UniversalCredits.UniversalCredits;
import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.handlers.BountyHandler;

public class BountyListener implements Listener {

	SurvivalGames plugin;
	
	public BountyListener ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
            Player killer = e.getEntity().getKiller();
            Player target = e.getEntity();
            
            for(BountyHandler bh : plugin.getBounties()){
            	if(bh.getPlaced().equals(target)){
            		if (killer != null && !killer.getName().equals(target.getName())) {
            			UniversalCredits.api.pay(killer.getName(), bh.getAmount(), false);
            			plugin.sendMessage(killer, ChatColor.GRAY + "You have go " + bh.getAmount() + " Credits for killing " + ChatColor.GOLD  + target.getName() + " and collecting thier bounty!");
            			plugin.sendMessage(target, ChatColor.GOLD + killer.getName() + ChatColor.GRAY + " was awarded " + bh.getAmount() + ChatColor.GRAY + " Credits for killing you because of your bounty!");
            		}else{
            			for (Map.Entry<String, Integer> entry : bh.getPlacers().entrySet()) {
            			    String key = entry.getKey();
            			    Integer value = entry.getValue();
            			    UniversalCredits.api.pay(key, value, false);
            			    Player player = Bukkit.getPlayer(key);
            			    if(player != null) plugin.sendMessage(player, ChatColor.GRAY + "You have been refunded " + value + " Credits because no one collected " + ChatColor.GOLD + bh.getPlaced() + ChatColor.GRAY + "'s bounty!");
            			}
            		}
            	}
            }
        }
    }
}
