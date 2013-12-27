package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdPlugins implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdPlugins ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("plugins")){
			if(args.length >= 0){
				plugin.sendMessage(player, ChatColor.GOLD + "Dedocated " + ChatColor.RED + "W" + ChatColor.BLUE + "a" + ChatColor.GREEN + "m.");
			}
		}
		
		return false;
	}
	
	
}
