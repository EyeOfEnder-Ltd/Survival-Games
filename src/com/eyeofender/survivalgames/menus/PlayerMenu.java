package com.eyeofender.survivalgames.menus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.handlers.SponsorHandler;

public class PlayerMenu implements Listener{
	public static Inventory myInventory = Bukkit.createInventory(null, 27, "Pic a player");

	static SurvivalGames plugin;
	
	public PlayerMenu (SurvivalGames plugin){
		this.plugin = plugin;
	}
	
	public static void init(){
		if(plugin.getGm().isInGame()){
			int i = 0;
			for(Player player : plugin.getGm().getIsAlive()){
				if(plugin.getGm().getIsAlive().size() >= i){
					createDisplay(Material.SKULL_ITEM, myInventory, i, player.getName(), ChatColor.BLUE + "Click to sponsor.");
					i++;
				}
			}
		}

	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.SKULL_ITEM) { 
				event.setCancelled(true); 
				Player playerr = Bukkit.getPlayer(clicked.getItemMeta().getDisplayName());
				SponsorHandler.giveSponsored(player, playerr);
				player.closeInventory(); 
			}else{
				event.setCancelled(true);
			}
		}
	}
	
	public static void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		 
		inv.setItem(Slot, item); 
		 
	}
}
