package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdRide implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdRide ( SurvivalGames plugin ){
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
			
			player.sendMessage(ChatColor.RED + "You can not use this command while in lobby.");
			return true;
		}
		
		if(CommandLabel.equalsIgnoreCase("example")){
			if(player.hasPermission("sg.rank.") || player.isOp()){
				if(args.length == 0){
					
				}else if(args.length == 1){
					
				}else{
					player.sendMessage(ChatColor.RED + "Too many arguments.");
				}
			}else{
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			}
		}
		
		return false;
	}
	
	
}
