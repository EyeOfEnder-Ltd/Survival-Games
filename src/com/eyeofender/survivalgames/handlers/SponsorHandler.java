package com.eyeofender.survivalgames.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.eyeofender.UniversalCredits.UniversalCredits;
import com.eyeofender.survivalgames.SurvivalGames;

public class SponsorHandler {

	public static Map<String, Material> sponsor = new HashMap<String, Material>();
	public static Map<String, Integer> amount = new HashMap<String, Integer>();

	static SurvivalGames plugin;
	
	public SponsorHandler ( SurvivalGames plugin ){
		SponsorHandler.plugin = plugin;
	}
	
	public static void setItem(Player player, Material m, int amountt){
		if(sponsor.containsKey(player.getName())){
			sponsor.remove(player.getName());
		}
		
		if(amount.containsKey(player.getName())){
			amount.remove(player.getName());
		}
		
		sponsor.put(player.getName(), m);
		amount.put(player.getName(), amountt);
	}
	
	public static void giveSponsored(Player player, Player amountt){
		if(!sponsor.containsKey(player.getName())){
			plugin.sendMessage(player, ChatColor.RED + "Something went wrong");
			return;
		}
		
		if(!amount.containsKey(player.getName())){
			plugin.sendMessage(player, ChatColor.RED + "Something went wrong");
			return;
		}
		
		if(!UniversalCredits.api.canAfford(player, amount.get(player.getName()))){
			plugin.sendMessage(player, ChatColor.RED + "You can not afford that item.");
			return;
		}
		
		UniversalCredits.api.withdraw(player.getName(), amount.get(player.getName()));
		amountt.getInventory().addItem(new ItemStack(sponsor.get(player.getName())));
		
		player.closeInventory();
		
		plugin.sendMessage(player, ChatColor.GRAY + "You have sponsored " + ChatColor.GOLD + amountt.getName() + ChatColor.GRAY + " with one " + sponsor.get(player.getName()).toString() + "!");
		plugin.sendMessage(amountt, ChatColor.GOLD + player.getName() + ChatColor.GRAY + "has sponsored you and given you one " + sponsor.get(player.getName()).toString() + "!");
	}
}
