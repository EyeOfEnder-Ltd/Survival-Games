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

public class ArmorMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 45, "Armor Menu");

	
	static {
		createDisplay(Material.CHAINMAIL_HELMET, myInventory, 1, "Chain Helmet", ChatColor.RED + "Cost: 375");
		createDisplay(Material.LEATHER_HELMET, myInventory, 3, "Leather Helmet", ChatColor.RED + "Cost: 375");
		createDisplay(Material.IRON_HELMET, myInventory, 5, "Iron Helmet", ChatColor.RED + "Cost: 1125");
		createDisplay(Material.GOLD_HELMET, myInventory, 7, "Gold Helmet", ChatColor.RED + "Cost: 1125");
		
		//Chestplates
		
		createDisplay(Material.CHAINMAIL_CHESTPLATE, myInventory, 10, "Chain Chestplate", ChatColor.RED + "Cost: 600");
		createDisplay(Material.LEATHER_CHESTPLATE, myInventory, 12, "Leather Chestplate", ChatColor.RED + "Cost: 600");
		createDisplay(Material.IRON_CHESTPLATE, myInventory, 14, "Iron Chestplate", ChatColor.RED + "Cost: 1800");
		createDisplay(Material.GOLD_CHESTPLATE, myInventory, 16, "Gold Chestplate", ChatColor.RED + "Cost: 1800");

		//Leggings
		
		createDisplay(Material.CHAINMAIL_LEGGINGS, myInventory, 19, "Chain Leggings", ChatColor.RED + "Cost: 525");
		createDisplay(Material.LEATHER_LEGGINGS, myInventory, 21, "Leather Leggings", ChatColor.RED + "Cost: 525");
		createDisplay(Material.IRON_LEGGINGS, myInventory, 23, "Iron Leggings", ChatColor.RED + "Cost: 1575");
		createDisplay(Material.GOLD_LEGGINGS, myInventory, 25, "Gold Leggings", ChatColor.RED + "Cost: 1575");
		
		//Leggings
		
		createDisplay(Material.CHAINMAIL_BOOTS, myInventory, 28, "Chain Boots", ChatColor.RED + "Cost: 300");
		createDisplay(Material.LEATHER_BOOTS, myInventory, 30, "Leather Boots", ChatColor.RED + "Cost: 300");
		createDisplay(Material.IRON_BOOTS, myInventory, 32, "Iron Boots", ChatColor.RED + "Cost: 900");
		createDisplay(Material.GOLD_BOOTS, myInventory, 34, "Gold Boots", ChatColor.RED + "Cost: 900");
		
		//Back
		createDisplay(Material.LAVA, myInventory, 40, "Back", "");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.CHAINMAIL_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.CHAINMAIL_HELMET, 375);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.LEATHER_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.LEATHER_HELMET, 375);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.IRON_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_HELMET, 1125);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.GOLD_HELMET) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_HELMET, 1125);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			// Chestplates
			
			else if (clicked.getType() == Material.CHAINMAIL_CHESTPLATE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.CHAINMAIL_CHESTPLATE, 600);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.LEATHER_CHESTPLATE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.LEATHER_CHESTPLATE, 600);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.IRON_CHESTPLATE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_CHESTPLATE, 1800);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.GOLD_CHESTPLATE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_CHESTPLATE, 1800);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			//Leggings
			
			else if (clicked.getType() == Material.CHAINMAIL_LEGGINGS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.CHAINMAIL_LEGGINGS, 525);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.LEATHER_LEGGINGS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.LEATHER_LEGGINGS, 525);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.IRON_LEGGINGS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_LEGGINGS, 1575);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.GOLD_LEGGINGS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_LEGGINGS, 1575);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			//BOOTS
			
			else if (clicked.getType() == Material.CHAINMAIL_BOOTS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.CHAINMAIL_BOOTS, 300);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.LEATHER_BOOTS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.LEATHER_BOOTS, 300);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.IRON_BOOTS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.IRON_BOOTS, 900);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}else if (clicked.getType() == Material.GOLD_BOOTS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLD_BOOTS, 900);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			//BACK
			
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
	//Leather = 75, Iron + Gold = 225
	
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
