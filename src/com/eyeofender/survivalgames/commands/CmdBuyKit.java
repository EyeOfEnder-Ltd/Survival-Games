package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdBuyKit implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdBuyKit ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("buyKit")){
			if(args.length == 0){
				plugin.sendMessage(player, ChatColor.RED + "Kit not found! Do /Kits to list your kits.");
			}else if(args.length == 1){
				plugin.getKm().buyKit(player, args[0]);
			}else{
				player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
