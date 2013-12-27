package com.eyeofender.survivalgames.menus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 9, "Perk Selection - Select a tier");

	
	static {
		createDisplay(Material.STICK, myInventory, 0, "Materials", "Contains Minerals, Crafting items.");
		createDisplay(Material.APPLE, myInventory, 2, "Food", "Contains food.");
		createDisplay(Material.STONE_HOE, myInventory, 4, "Tools", "Contains tools.");
		createDisplay(Material.STONE_HOE, myInventory, 4, "Weapons", "Contains weapons.");
		createDisplay(Material.STONE_HOE, myInventory, 4, "Armor", "Contains armor.");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.IRON_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				//player.openInventory(Tier1PerkMenu.myInventory);
			}else if (clicked.getType() == Material.GOLD_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				//player.openInventory(Tier2PerkMenu.myInventory);
				player.sendMessage(ChatColor.RED + "This feature is currently under inspection!");
			}else if (clicked.getType() == Material.DIAMOND_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				//player.openInventory(Tier3PerkMenu.myInventory);
				player.sendMessage(ChatColor.RED + "This feature is currently under inspection!");
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
