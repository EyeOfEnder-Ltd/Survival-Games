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

import com.eyeofender.survivalgames.handlers.SponsorHandler;

public class ToolMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 18, "Perk Selection - Select a tier");

	
	static {
		createDisplay(Material.WOOD_AXE, myInventory, 1, "Wood Axe", ChatColor.RED + "Cost: 250");
		createDisplay(Material.STONE_AXE, myInventory, 3, "Stone Axe", ChatColor.RED + "Cost: 250");
		createDisplay(Material.GOLD_AXE, myInventory, 5, "Gold Axe", ChatColor.RED + "Cost: 700");
		createDisplay(Material.IRON_AXE, myInventory, 7, "Iron Axe", ChatColor.RED + "Cost: 700");
		createDisplay(Material.LAVA, myInventory, 13, "Back", ChatColor.RED + "");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.WOOD_AXE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.WOOD_AXE, 250);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.STONE_AXE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.STONE_AXE, 250);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.GOLD_AXE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_AXE, 700);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.IRON_AXE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_AXE, 700);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.LAVA) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(SponsorMenu.myInventory);
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
