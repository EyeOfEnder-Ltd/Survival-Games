package com.eyeofender.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdGoTo implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdGoTo ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(plugin.getGm().getIsAlive().contains(player)){
			player.sendMessage(ChatColor.RED + "You can not use this command while in game.");
			return true;
		}
		
		if(CommandLabel.equalsIgnoreCase("goto")){
			if(args.length == 0){
				plugin.sendMessage(player, ChatColor.RED + "Please name a player to go to.");
			}else if(args.length == 1){
				Player targetPlayer = Bukkit.getPlayer(args[0]);
				
				if(targetPlayer == null){
					plugin.sendMessage(player, "Target player not found.");
					return true;
				}
				
				player.teleport(targetPlayer.getLocation().clone().add(0.5D, 0.5D, 0.5D));
				plugin.sendMessage(player, ChatColor.GOLD + "You have teleported to " + targetPlayer.getName() + ".");
			}else{
				player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
