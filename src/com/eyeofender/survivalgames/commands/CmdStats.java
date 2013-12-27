package com.eyeofender.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.models.Stats;

public class CmdStats implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdStats ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		Stats stats = null;
		
		if (args.length == 0) {
            stats = (Stats) plugin.getSs().playerStats.get(player.getName());
            if (stats == null) player.sendMessage(ChatColor.RED + player.getName() + "'s stats has not loaded yet");
            stats.endTimeLog();
	        stats.startTimeLog();
	        sender.sendMessage(ChatColor.DARK_AQUA + "-- Now displaying " + stats.getName() + " stats --");
	        sender.sendMessage(ChatColor.BLUE + "Wins: " + ChatColor.AQUA + stats.getWins());
	        sender.sendMessage(ChatColor.BLUE + "Passes: " + ChatColor.AQUA + stats.getMutationPasses());
	        sender.sendMessage(ChatColor.BLUE + "Losses: " + ChatColor.AQUA + stats.getLosses());
	        sender.sendMessage(ChatColor.BLUE + "Games played: " + ChatColor.AQUA + (stats.getWins() + stats.getLosses()));
	        sender.sendMessage(ChatColor.BLUE + "Total kills: " + ChatColor.AQUA + stats.getKills());
	        sender.sendMessage(ChatColor.BLUE + "Total death: " + ChatColor.AQUA + stats.getDeaths());
	        sender.sendMessage(ChatColor.BLUE + "Best kill streak: " + ChatColor.AQUA + stats.getHighestKillStreak());
	        sender.sendMessage(ChatColor.BLUE + "Time logged: " + ChatColor.AQUA + stats.getTime(stats.getLogged()));
	        sender.sendMessage(ChatColor.DARK_AQUA + "-- End of " + stats.getName() + "'s stats --");
        }else if(args.length == 0){
            stats = (Stats) plugin.getSs().playerStats.get(args[0]);
            if (stats == null) player.sendMessage(ChatColor.RED + player.getName() + "'s stats has not loaded yet");
            stats.endTimeLog();
	        stats.startTimeLog();
	        sender.sendMessage(ChatColor.DARK_AQUA + "-- Now displaying " + stats.getName() + " stats --");
	        sender.sendMessage(ChatColor.BLUE + "Wins: " + ChatColor.AQUA + stats.getWins());
	        sender.sendMessage(ChatColor.BLUE + "Passes: " + ChatColor.AQUA + stats.getMutationPasses());
	        sender.sendMessage(ChatColor.BLUE + "Losses: " + ChatColor.AQUA + stats.getLosses());
	        sender.sendMessage(ChatColor.BLUE + "Games played: " + ChatColor.AQUA + (stats.getWins() + stats.getLosses()));
	        sender.sendMessage(ChatColor.BLUE + "Total kills: " + ChatColor.AQUA + stats.getKills());
	        sender.sendMessage(ChatColor.BLUE + "Total death: " + ChatColor.AQUA + stats.getDeaths());
	        sender.sendMessage(ChatColor.BLUE + "Best kill streak: " + ChatColor.AQUA + stats.getHighestKillStreak());
	        sender.sendMessage(ChatColor.BLUE + "Time logged: " + ChatColor.AQUA + stats.getTime(stats.getLogged()));
	        sender.sendMessage(ChatColor.DARK_AQUA + "-- End of " + stats.getName() + "'s stats --");
        }
		return false;
	}
	
	
}
