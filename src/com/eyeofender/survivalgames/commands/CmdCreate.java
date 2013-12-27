package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdCreate implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdCreate ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("create")){
			if(player.isOp()){
				if(args.length == 0){
					player.sendMessage(ChatColor.RED + "Usage: /Create (Map)");
				}else if(args.length == 1){
					plugin.getGc().createMap(player, args[0]);
				}else if(args.length == 2){
					plugin.getGc().createMap(player, args[0], args[1]);
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
