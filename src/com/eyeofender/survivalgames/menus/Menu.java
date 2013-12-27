package com.eyeofender.survivalgames.menus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eyeofender.survivalgames.SurvivalGames;

public class Menu implements Listener{
	public static Inventory myInventory = Bukkit.createInventory(null, 18, "Search & Destroy!");

	SurvivalGames plugin;
	
	public Menu (SurvivalGames plugin){
		this.plugin = plugin;
	}
	
	static {
		createDisplay(Material.COMPASS, myInventory, 1, "Spectate", "Click to open spectate menu.");
		createDisplay(Material.MOB_SPAWNER, myInventory, 3, "Resurrection Pass", "Click to use resurrection pass.");
		createDisplay(Material.BOOK_AND_QUILL, myInventory, 5, "Help", "Click to open help menu.");
		createDisplay(Material.DIAMOND, myInventory, 7, "Sponsor", "Click to open sponsor menu.");
	}
	
	public void loadListeners(){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getServer().getPluginManager().registerEvents(new ArmorMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new FoodMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new MaterialMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new SponsorMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new ToolMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new WeaponMenu(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new PlayerMenu(plugin), plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(myInventory.getName())) { 
			if (clicked.getType() == Material.COMPASS) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				SpectateMenu.init();
				player.openInventory(SpectateMenu.myInventory);
			}else if (clicked.getType() == Material.DIAMOND) { 
				if(plugin.getGm().isInGame()){
					event.setCancelled(true); 
					player.closeInventory(); 
					player.openInventory(SponsorMenu.myInventory);
				}else{
					event.setCancelled(true);
					player.closeInventory();
					plugin.sendMessage(player, ChatColor.RED + "You can not sponsor people before game.");
				}
			}else if (clicked.getType() == Material.MOB_SPAWNER) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				//player.openInventory(SpectateMenu.myInventory);
				plugin.sendMessage(player, "Resurrection passes are getting almost done. Maybe next update!");
			}else if (clicked.getType() == Material.BOOK_AND_QUILL) { 
				event.setCancelled(true); 
				player.closeInventory(); 
				plugin.sendMessage(player, "Help menu has not been finished.");
				//player.openInventory(SpectateMenu.myInventory);
			}else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onMenuClick(PlayerInteractEvent event){
		if(event.getItem() == null)
			return;
		
		if((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getType() == Material.COMPASS){
			event.getPlayer().openInventory(myInventory);
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
