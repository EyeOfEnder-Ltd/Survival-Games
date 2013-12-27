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

public class FoodMenu implements Listener {
	public static Inventory myInventory = Bukkit.createInventory(null, 45, "Food Menu");

	static {
		createDisplay(Material.POTATO, myInventory, 0, "Potato", ChatColor.RED + "Cost: 25");
		createDisplay(Material.COOKIE, myInventory, 2, "Cookie", ChatColor.RED + "Cost: 50");
		createDisplay(Material.MELON, myInventory, 4, "Melon", ChatColor.RED + "Cost: MELONMELON");
		createDisplay(Material.RAW_CHICKEN, myInventory, 6, "Raw Chicken", ChatColor.RED + "Cost: 50");
		createDisplay(Material.RAW_FISH, myInventory, 8, "Raw Fish", ChatColor.RED + "Cost: 50");
		createDisplay(Material.RAW_BEEF, myInventory, 10, "Raw Beef", ChatColor.RED + "Cost: 75");
		createDisplay(Material.APPLE, myInventory, 12, "Apple", ChatColor.RED + "Cost: 100");
		createDisplay(Material.CARROT, myInventory, 14, "Carrot", ChatColor.RED + "Cost: 100");
		createDisplay(Material.BREAD, myInventory, 16, "Bread", ChatColor.RED + "Cost: 125");
		createDisplay(Material.COOKED_FISH, myInventory, 18, "Cooked Fish", ChatColor.RED + "Cost: 125");
		createDisplay(Material.BAKED_POTATO, myInventory, 20, "Baked Potato", ChatColor.RED + "Cost: 150");
		createDisplay(Material.COOKED_CHICKEN, myInventory, 22, "Cooked Chicken", ChatColor.RED + "Cost: 150");
		createDisplay(Material.GOLDEN_CARROT, myInventory, 24, "Golden Carrot", ChatColor.RED + "Cost: 150");
		createDisplay(Material.MUSHROOM_SOUP, myInventory, 26, "Mushroom Soup", ChatColor.RED + "Cost: 150");
		createDisplay(Material.PORK, myInventory, 28, "Pork", ChatColor.RED + "Cost: 200");
		createDisplay(Material.PUMPKIN_PIE, myInventory, 30, "Pumpkin Pie", ChatColor.RED + "Cost: 200");
		createDisplay(Material.COOKED_BEEF, myInventory, 32, "Steak", ChatColor.RED + "Cost: 200");
		createDisplay(Material.GOLDEN_APPLE, myInventory, 34, "Golden Apple", ChatColor.RED + "Cost: 400");
		createDisplay(Material.LAVA, myInventory, 40, "Back", ChatColor.RED + "");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.POTATO) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.POTATO, 25);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.PUMPKIN_PIE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.PUMPKIN_PIE, 200);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.COOKED_BEEF) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.COOKED_BEEF, 200);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.GOLDEN_APPLE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLDEN_APPLE, 400);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.GOLDEN_CARROT) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.GOLDEN_CARROT, 150);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.MUSHROOM_SOUP) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.MUSHROOM_SOUP, 150);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.PORK) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.PORK, 200);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.COOKED_FISH) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.COOKED_FISH, 125);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.BAKED_POTATO) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.BAKED_POTATO, 150);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.COOKED_CHICKEN) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.COOKED_CHICKEN, 150);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.APPLE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.APPLE, 100);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.CARROT) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.CARROT, 100);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.BREAD) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.BREAD, 125);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.RAW_CHICKEN) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.RAW_CHICKEN, 50);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.RAW_FISH) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.RAW_FISH, 50);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.RAW_BEEF) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.RAW_BEEF, 75);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.COOKIE) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.COOKIE, 50);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.MELON) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.MELON, 50);
				PlayerMenu.init();
				player.openInventory(PlayerMenu.myInventory);
			}
			
			else if (clicked.getType() == Material.RAW_CHICKEN) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SponsorHandler.setItem(player, Material.RAW_CHICKEN, 50);
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
