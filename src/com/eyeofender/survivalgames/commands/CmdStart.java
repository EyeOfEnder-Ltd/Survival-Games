package com.eyeofender.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdStart implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdStart ( SurvivalGames plugin ){
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
		}
		
		if(CommandLabel.equalsIgnoreCase("start")){
			if(player.isOp()){
				if(args.length == 0){
					plugin.sendMessage(player, ChatColor.RED + "Usage: /Start (Time)");
					plugin.getGm().useResurrection(player);
				}else if(args.length == 1){
					plugin.getLobbyTimer().timer = -Integer.parseInt(args[0]);
					
					for(Player players : Bukkit.getOnlinePlayers()){
						players.sendMessage(ChatColor.GOLD + player.getName() + " has set the lobby timer to " + args[0] + "!");
					}
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
