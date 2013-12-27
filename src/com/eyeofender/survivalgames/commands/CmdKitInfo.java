package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.util.Kit;

public class CmdKitInfo implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdKitInfo ( SurvivalGames plugin ){
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
		
		if(CommandLabel.equalsIgnoreCase("kitInfo")){
			if(args.length == 0){
				plugin.sendMessage(player, ChatColor.RED + "Please name a kit to search.");
			}else if(args.length == 1){
				Kit kit = plugin.getKm().getKitByName(args[0]);
				
				if(kit != null){
					player.sendMessage("");
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
					player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Kit: " + ChatColor.RESET + ChatColor.GRAY + kit.getName());
					player.sendMessage("");
					player.sendMessage("" + ChatColor.BLUE + ChatColor.BOLD + "Description: " + ChatColor.RESET + ChatColor.GRAY + kit.getDescription());
					player.sendMessage("");
					player.sendMessage("" + ChatColor.YELLOW + ChatColor.BOLD + "Items: " + ChatColor.RESET + ChatColor.GRAY + kit.getItemDescription());
					player.sendMessage("");
					player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "Cost: " + ChatColor.RESET + ChatColor.GRAY + kit.getCost());
					player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
				}else{
					plugin.sendMessage(player, ChatColor.RED + "Kit not found.");
				}
				
			}else{
				player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
