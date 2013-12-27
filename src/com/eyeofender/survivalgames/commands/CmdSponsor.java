package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.handlers.SponsorHandler;
import com.eyeofender.survivalgames.menus.SponsorMenu;

public class CmdSponsor implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdSponsor ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(!plugin.getGm().isInGame()){
			player.sendMessage(ChatColor.RED + "You can not use this command while in lobby.");
			return true;
		}
		
		if(CommandLabel.equalsIgnoreCase("sponsor")){
			if(args.length >= 0){
				player.openInventory(SponsorMenu.myInventory);
			}else{
					player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
