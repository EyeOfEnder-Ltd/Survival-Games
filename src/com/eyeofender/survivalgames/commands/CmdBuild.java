package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdBuild implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdBuild ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("build")){
			if(player.isOp()){
				if(args.length == 0){
					if(plugin.getBuild().contains(player)){
						plugin.getBuild().remove(player);
						plugin.sendMessage(player, ChatColor.GRAY + "You are not able to build!");
					}else{
						plugin.getBuild().add(player);
						plugin.sendMessage(player, ChatColor.GRAY + "You are able to build!");
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
