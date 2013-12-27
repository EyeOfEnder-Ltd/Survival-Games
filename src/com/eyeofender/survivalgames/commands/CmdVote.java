package com.eyeofender.survivalgames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdVote implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdVote ( SurvivalGames plugin ){
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
		
		if(CommandLabel.equalsIgnoreCase("vote")){
			if(args.length == 0){
				plugin.getVh().listMap(player);
			}else if(args.length == 1){
				plugin.getVh().vote(player, args[0]);
			}else if(args.length == 2){
				plugin.getVh().vote(player, args[0], args[1]);
			}else{
				player.sendMessage("Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
