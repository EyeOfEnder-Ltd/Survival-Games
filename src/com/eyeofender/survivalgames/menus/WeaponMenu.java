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

public class WeaponMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 27, "Weapon Menu");

	
	static {
		createDisplay(Material.WOOD_SWORD, myInventory, 1, "Wood Sword", ChatColor.RED + "Cost: 175");
		createDisplay(Material.STONE_SWORD, myInventory, 3, "Stone Sword", ChatColor.RED + "Cost: 350");
		createDisplay(Material.GOLD_SWORD, myInventory, 5, "Gold Sword", ChatColor.RED + "Cost: 350");
		createDisplay(Material.IRON_SWORD, myInventory, 7, "Iron Sword", ChatColor.RED + "Cost: 475");
		createDisplay(Material.BOW, myInventory, 11, "Bow", "Cost: 150");
		createDisplay(Material.ENDER_PEARL, myInventory, 13, "Ender Pearl", ChatColor.RED + "Cost: 175");
		createDisplay(Material.ARROW, myInventory, 15, "Arrow", "Cost: 25");
		createDisplay(Material.LAVA, myInventory, 21, "Back", "");
		createDisplay(Material.LAVA, myInventory, 23, "Back", "");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.WOOD_SWORD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.WOOD_SWORD, 175);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.STONE_SWORD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.STONE_SWORD, 350);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.GOLD_SWORD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_SWORD, 350);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.IRON_SWORD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_SWORD, 475);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.BOW) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.BOW, 150);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.ENDER_PEARL) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.ENDER_PEARL, 175);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.ARROW) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.ARROW, 25);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.LAVA) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				player.openInventory(SponsorMenu.myInventory);
			}
			
			else{
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
