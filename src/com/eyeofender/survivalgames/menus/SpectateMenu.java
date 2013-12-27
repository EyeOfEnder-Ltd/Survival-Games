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

import com.eyeofender.survivalgames.SurvivalGames;

public class SpectateMenu implements Listener{
	static SurvivalGames plugin;
	
	public static Inventory myInventory = Bukkit.createInventory(null, 27, "Spectate menu");
	
	public SpectateMenu (SurvivalGames plugin){
		SpectateMenu.plugin = plugin;
	}
	
	public static void init(){
		int i = 0;
		for(Player player : plugin.getGm().getIsAlive()){
			if(plugin.getGm().getIsAlive().size() >= i){
				createDisplay(Material.SKULL_ITEM, myInventory, i, player.getName(), ChatColor.BLUE + "Click to spectate.");
				i++;
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
				if(!plugin.getGm().getIsAlive().contains(player)){
					event.setCancelled(true); 
					Player playerr = Bukkit.getPlayer(clicked.getItemMeta().getDisplayName());
					player.closeInventory(); 
					player.teleport(playerr.getLocation().clone().add(0.5D, 0.5D, 0.5D));
				}else{
					plugin.sendMessage(player, ChatColor.RED + "You are in game. You can not do this.");
				}
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
