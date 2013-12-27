package com.eyeofender.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.eyeofender.UniversalCredits.UniversalCredits;
import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.handlers.BountyHandler;

public class CmdBounty implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdBounty ( SurvivalGames plugin ){
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
		
		if(CommandLabel.equalsIgnoreCase("bounty")){
			if(args.length == 0){
				plugin.sendMessage(player, ChatColor.RED + "Usage: /Bounty (Player) (Amount)");
			}else if(args.length == 1){
				plugin.sendMessage(player, ChatColor.RED + "Usage: /Bounty (Player) (Amount)");
			}else if(args.length == 2){
				int amount;
                try {
                	amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    plugin.sendMessage(player, ChatColor.RED + "That is not a valid number!");
                    return true;
                }
                
				Player targerPlayer = Bukkit.getPlayer(args[0]);
				if(targerPlayer == null){
					plugin.sendMessage(player, ChatColor.RED + "Target player not found.");
					return true;
				}
				
				if(!UniversalCredits.api.canAfford(player, amount)){
					plugin.sendMessage(player, ChatColor.RED + "You can not afford that much!");
					return true;
				}
				
				if(targerPlayer == player){
					plugin.sendMessage(player, "You can not set a bounty on yourself.");
					return true;
				}
				
				UniversalCredits.api.withdraw(player.getName(), amount);
				
				boolean placed = false;
				
				for(BountyHandler bh : plugin.getBounties()){
					if(bh.getPlaced().equals(targerPlayer)){
						placed = true;
					}
				}
				
				if(placed){
					BountyHandler bh = null;
					for(BountyHandler bhh : plugin.getBounties()){
						if(bhh.getPlaced().equals(targerPlayer)){
							bh = bhh;
						}
					}
					
					bh.getPlacers().put(player.getName(), amount);
					
					plugin.sendMessage(targerPlayer, ChatColor.GRAY + player.getName() + " has added " + amount + " Credits to your bounty of " + bh.getAmount() +  "Credits to become " + (amount + bh.getAmount()) + " Credits!");
					plugin.sendMessage(player, ChatColor.GRAY + "You have added " + amount + " Credits to " + ChatColor.GOLD + targerPlayer.getName() + "s " + ChatColor.GRAY + "bounty of " + bh.getAmount() +  " Credits to become " + (amount + bh.getAmount()) + " Credits!");
					bh.setAmount(bh.getAmount() + amount);
					
					for(Player players : Bukkit.getOnlinePlayers()){
						plugin.sendMessage(players, ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has added " + amount + " Credits to " + ChatColor.GOLD + targerPlayer.getName() + "s " + ChatColor.GRAY + "bounty of " + bh.getAmount() +  " Credits to become " + (amount + bh.getAmount()) + " Credits!");
					}
				}else{
					BountyHandler bh = new BountyHandler(targerPlayer, amount);
					bh.getPlacers().put(player.getName(), amount);
					plugin.getBounties().add(bh);
					plugin.sendMessage(targerPlayer, ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has set a bounty of " + amount + " on you!");
					plugin.sendMessage(player, ChatColor.GRAY + "You have set a bounty of " + amount + " on " + ChatColor.GOLD + targerPlayer.getName() + ChatColor.GRAY + "!");
					
					for(Player players : Bukkit.getOnlinePlayers()){
						plugin.sendMessage(players, ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has set a bounty of " + amount + " on " + ChatColor.GOLD + targerPlayer.getName() + "!");
					}
				}
				
			}else{
				player.sendMessage(ChatColor.RED + "Too many arguments.");
			}
		}
		
		return false;
	}
	
	
}
