package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdTop implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdTop ( SurvivalGames plugin ){
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
		
		if(CommandLabel.equalsIgnoreCase("top")){
			if(args.length >= 0){
				player.teleport(player.getWorld().getHighestBlockAt(player.getLocation()).getLocation());
				plugin.sendMessage(player, ChatColor.GOLD + "Teleported to the top.");
			}else{
				player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
