package com.eyeofender.survivalgames.handlers;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

import com.eyeofender.survivalgames.SurvivalGames;

public class ResurrectionHandler {

	SurvivalGames plugin;
	
	public ResurrectionHandler ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(Player player){
		setAlive(player);
		turnIntoZombie(player);
		spawnPlayer(player);
	}
	
	public void setAlive(Player player){
		player.showPlayer(player);
		plugin.getGm().getResurrected().add(player);
	}
	
	public void turnIntoZombie(Player player){
		if (plugin.getDcAPI().isDisguised(player)) {
			plugin.getDcAPI().undisguisePlayer(player);
		}
		
		Disguise d = new Disguise(plugin.getDcAPI().newEntityID(), DisguiseType.Zombie);
		try{
			plugin.getDcAPI().disguisePlayer(player, d);
			player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 2));
			player.getInventory().addItem(new ItemStack(Material.GOLD_SWORD));
			
			for(Player players : Bukkit.getOnlinePlayers()){
				plugin.sendMessage(players, ChatColor.GOLD +  player.getName() + ChatColor.GRAY + " has used a resurrection pass and has been resurrected to kill " + ChatColor.GOLD  + plugin.getGm().findGamePlayer(player).getKiller() + ChatColor.GRAY + "!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void spawnPlayer(Player player){
		player.teleport(plugin.getGame().getSpawns().get(0).clone().add(0.5D, 0.5D, 0.5D));
	}

	public void checkRessurrected() {
		for(Player player : plugin.getGm().getResurrected()){
			if(!plugin.getGm().getIsAlive().contains(plugin.getGm().findGamePlayer(player).getKiller())){
				setRampage(player);
			}
		}
	}
	
	public void removePlayer(Player player){
		plugin.getGm().getResurrected().remove(player);
		plugin.getGm().clearPotionEffects(player);
		player.getInventory().clear();
		player.getInventory().setBoots(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setHelmet(null);
		
		if (plugin.getDcAPI().isDisguised(player)) {
			plugin.getDcAPI().undisguisePlayer(player);
		}
		
		for(Player players : Bukkit.getOnlinePlayers()){
			plugin.sendMessage(players, ChatColor.GOLD +  player.getName() + ChatColor.GRAY + " has killed thier target of " + ChatColor.GOLD  + plugin.getGm().findGamePlayer(player).getKiller() + ChatColor.GRAY + " and going to live again.");
		}
	}
	
	public void setRampage(Player player){
		for(Player players : Bukkit.getOnlinePlayers()){
			plugin.sendMessage(players, ChatColor.GOLD +  player.getName() + ChatColor.GRAY + " objective was killed, " + ChatColor.GOLD  + player.getName() + ChatColor.GRAY + " is going to go on a rampage. Kill him at once.");
		}
		
		plugin.sendMessage(player, ChatColor.GRAY + "Since someone has killed your killer. You can go on a rampage and kill anyone.");
		
		plugin.getGm().getRampage().add(player);
	}
}
