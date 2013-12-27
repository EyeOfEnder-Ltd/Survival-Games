package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.menus.Menu;

public class CmdUpdate implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdUpdate ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("update")){
			if(player.isOp()){
				if(args.length == 0){
					player.sendMessage(ChatColor.RED + "Usage: /Update Waypoint");
					player.openInventory(Menu.myInventory);
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("world"))
						plugin.getGc().updateArenaWorld(player);
					else if(args[0].equalsIgnoreCase("lobby"))
						plugin.getGc().updateLobby(player);
					else if(args[0].equalsIgnoreCase("spawn"))
						plugin.getGc().updateSpawns(player);
					else if(args[0].equalsIgnoreCase("dmspawn"))
						plugin.getGc().updateDeathMatchSpawns(player);
					else if(args[0].equalsIgnoreCase("firework"))
						plugin.getGc().updateFirework(player);
					else
						player.sendMessage(ChatColor.RED + "Waypoint not found.");
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
