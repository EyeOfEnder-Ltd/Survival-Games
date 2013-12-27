package com.eyeofender.survivalgames.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.util.Kit;

public class CmdKit implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdKit ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(plugin.getGm().isInGame()){
			player.sendMessage(ChatColor.RED + "You can not use this command while in game.");
			return true;
		}
		
		if(CommandLabel.equalsIgnoreCase("kit")){
				if(args.length == 0){

					plugin.sendMessage(player, ChatColor.RED + "Kit not found! Do /Kits to list your kits.");
					
				}else if(args.length == 1){

					Kit kit = plugin.getKm().getKitByName(args[0]);
			       
			        /** Checking if the kit exists **/
			        if (kit == null) {
				          plugin.sendMessage(player, ChatColor.RED + "This kit does not exist!");
				          return true;
				    }
			        
			        List<Kit> hasKits = plugin.getKm().hisKits(player);
			        Kit playerKit = plugin.getKm().getKitByPlayer(player.getName());
			        
			        if(!hasKits.contains(kit)){
			        	plugin.sendMessage(player, ChatColor.RED + "You do not own this kit.");
			        	return true;
			        }
			        
			        if (playerKit != null){
			        	playerKit.removePlayer(player.getName());
			        }
			        
			        kit.addPlayer(player.getName());
			        
			        plugin.sendMessage(player, ChatColor.GOLD + "Now using kit " + kit.getName() + ChatColor.GOLD + "!");

				}else{
					player.sendMessage(ChatColor.RED + "Too many arguments.");
				}
			}else{
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			}
		
		return false;
	}
	
	
}
