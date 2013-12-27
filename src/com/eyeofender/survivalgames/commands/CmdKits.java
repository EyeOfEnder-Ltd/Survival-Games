package com.eyeofender.survivalgames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.util.Kit;

public class CmdKits implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdKits ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("kits")){
			if(args.length >= 0){
				/** Kit Lists **/
			    List<String> ownedKits = new ArrayList<String>();
			    List<String> notOwnedKits = new ArrayList<String>();
			    
			    /** Finds all the kits the player owns **/
			    for(Kit kits : plugin.getKm().hisKits(player))
			    	ownedKits.add(kits.getName());
			    
			    /** Gets all the kits the player does not own **/
			    for(Kit kits : plugin.getKm().otherKits(player))
			    	notOwnedKits.add(kits.getName());
			    
			    /** Sorts kits **/
			    Collections.sort(ownedKits, String.CASE_INSENSITIVE_ORDER);
			    Collections.sort(notOwnedKits, String.CASE_INSENSITIVE_ORDER);
			    
			    player.sendMessage("");
			    
				player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
				
				player.sendMessage("");
				
				 if (plugin.getKm().getKitByPlayer(player.getName()) != null){
					 plugin.sendMessage(player, "" + ChatColor.BLUE + ChatColor.BOLD + "Your current kit: " + ChatColor.RESET + ChatColor.GRAY + plugin.getKm().getKitByPlayer(player.getName()).getName());
				 }else{
				     plugin.sendMessage(player, "" + ChatColor.BLUE + ChatColor.BOLD + "Your current kit: " + ChatColor.RESET + ChatColor.RED + "None");
				 }
				 
				 player.sendMessage("");
				 
				 if(ownedKits.size() != 0){
				     String list = StringUtils.join(ownedKits, "" + ChatColor.RESET + ChatColor.GRAY + ", ");
					 plugin.sendMessage(player, "" + ChatColor.GREEN + ChatColor.BOLD + "Your kits: " + ChatColor.GRAY + list + ChatColor.GRAY + "." );
				 }else{
					 plugin.sendMessage(player, "" + ChatColor.GREEN + ChatColor.BOLD + "Your kits: " + ChatColor.RED + "You do not own any kits!");
				 }
				 
				 player.sendMessage("");
				 
				 if(notOwnedKits.size() != 0){
				     String list = StringUtils.join(notOwnedKits, "" + ChatColor.RESET + ChatColor.GRAY + ", ");
				     plugin.sendMessage(player, "" + ChatColor.RED + ChatColor.BOLD + "Other Kits: " + ChatColor.GRAY + list + ChatColor.GRAY + ".");
				 }else{
					 plugin.sendMessage(player, "" + ChatColor.RED + ChatColor.BOLD + "Other Kits: " + ChatColor.RED + "You do not own any kits!");
				 }
				 
				player.sendMessage("");
				 
				player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
				
			}
		}else{
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		}
		
		return false;
	}
	
	
}
