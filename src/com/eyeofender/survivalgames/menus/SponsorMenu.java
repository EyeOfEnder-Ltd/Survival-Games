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

public class SponsorMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 9, "Sponsor Menu");

	
	static {
		createDisplay(Material.STICK, myInventory, 0, "Materials", "Contains Minerals, Crafting items.");
		createDisplay(Material.APPLE, myInventory, 2, "Food", "Contains food.");
		createDisplay(Material.STONE_HOE, myInventory, 4, "Tools", "Contains tools.");
		createDisplay(Material.WOOD_SWORD, myInventory, 6, "Weapons", "Contains weapons.");
		createDisplay(Material.GOLD_HELMET, myInventory, 8, "Armor", "Contains armor.");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			
			//MATERIAL MENU
			
			if (clicked.getType() == Material.STICK) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				//player.openInventory(MaterialMenu.myInventory);
				player.sendMessage(ChatColor.RED + "In development.");
			}
			
			//FOOD MENU
			
			else if (clicked.getType() == Material.APPLE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(FoodMenu.myInventory);
			}
			
			//TOOL MENU
			
			else if (clicked.getType() == Material.STONE_HOE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(ToolMenu.myInventory);
			}
			
			//WEAPON MENU
			
			else if (clicked.getType() == Material.WOOD_SWORD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(WeaponMenu.myInventory);
			}
			
			//ARMOR MENU
			
			else if (clicked.getType() == Material.GOLD_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(ArmorMenu.myInventory);
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
