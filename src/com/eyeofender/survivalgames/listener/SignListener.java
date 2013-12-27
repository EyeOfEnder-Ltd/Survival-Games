package com.eyeofender.survivalgames.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

	@EventHandler
	public void onSignCreate ( SignChangeEvent event ){
		if(event.getLine(0).equalsIgnoreCase("[SG]")){
			/** Back to hub **/
			if(event.getLine(1).equalsIgnoreCase("Hub")){
				event.setLine(1, ChatColor.RED + "[Ender]");
				event.setLine(2, ChatColor.BLUE + "Back to hub.");
			}
			
			/** Voting for a map **/
			if(event.getLine(1).equalsIgnoreCase("Vote")){
				event.setLine(1, ChatColor.RED + "[Vote]");
				event.setLine(2, ChatColor.BLUE + event.getLine(2));
			}
		}
	}
	
	@EventHandler
	public void onSignClick ( PlayerInteractEvent event ){
		 if (event.isCancelled()) return;
	        
		 if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType() == Material.WALL_SIGN) {
			 org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();

			 if(sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "[Ender]")){
				 if(sign.getLine(2).equalsIgnoreCase(ChatColor.BLUE + "Back to hub.")){
					 event.getPlayer().kickPlayer("Teleported to the hub.");
				 }
			 }
			 
			 if(sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "[Vote]")){
				 String arena = sign.getLine(2).toLowerCase();
				 
				 if(arena == "T_Heights"){
					 arena = "treacherous heights";
				 }
				 
				 event.getPlayer().performCommand("vote " + arena);
			 }
		 }
	}
}
