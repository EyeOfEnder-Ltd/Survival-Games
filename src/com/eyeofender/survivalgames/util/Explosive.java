package com.eyeofender.survivalgames.util;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.survivalgames.handlers.KitHandler;

public class Explosive
  implements Listener
{
	KitHandler plugin;

  public Explosive(KitHandler main)
  {
    this.plugin = main;
  }

  @EventHandler
  public void onArrowShot(ProjectileHitEvent event) {
    if (((event.getEntity().getShooter() instanceof Player)) && 
      (event.getEntity().hasMetadata("Explode"))) {
      final Location hitSpot = event.getEntity().getLocation();
      final Entity entity = event.getEntity();
      if (event.getEntityType() == EntityType.ARROW)
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin.plugin, new Runnable() {
          public void run() {
            hitSpot.getWorld().createExplosion(hitSpot, 
              ((MetadataValue)entity.getMetadata("Explode").get(0)).asFloat());
            entity.remove();
          }
        }
        , 0L);
    }
  }

  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event)
  {
    if (((event.getDamager() instanceof Arrow)) && 
      (event.getDamager().hasMetadata("Poison")) && 
      ((event.getEntity() instanceof LivingEntity)))
    {
      if (event
        .getEntity()
        .getLocation()
        .distance(
        (Location)((MetadataValue)event.getDamager().getMetadata("PoisonLoc").get(0))
        .value()) < 30.0D)
        return;
      int ticks = ((MetadataValue)event.getDamager().getMetadata("Poison").get(0)).asInt() * 100;

      Iterator localIterator = ((LivingEntity)event.getEntity())
        .getActivePotionEffects().iterator();

      while (localIterator.hasNext()) {
        PotionEffect effect = (PotionEffect)localIterator.next();
        if (effect.getType().equals(PotionEffectType.POISON)) {
          ticks += effect.getDuration();
          break;
        }
      }
      ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(
        PotionEffectType.POISON, ticks, 1), true);
    }
  }

  @EventHandler
  public void onArrowShoot(ProjectileLaunchEvent event) {
    if (((event.getEntity() instanceof Arrow)) && 
      (event.getEntity().getShooter() != null) && 
      ((event.getEntity().getShooter() instanceof Player))) {
      Player shooter = (Player)event.getEntity().getShooter();
      ItemStack item = shooter.getItemInHand();
      if ((item != null) && (item.getType() != Material.AIR)) {
        if (item.containsEnchantment(Enchants.EXPLOSIVE)) {
          event.getEntity().setMetadata(
            "Explode", 
            new FixedMetadataValue(this.plugin.plugin, 
            Float.valueOf(item
            .getEnchantmentLevel(Enchants.EXPLOSIVE) * 1.5F)));
        }
        if (item.containsEnchantment(Enchants.POISON)) {
          event.getEntity().setMetadata(
            "Poison", 
            new FixedMetadataValue(this.plugin.plugin, 
            Integer.valueOf(item
            .getEnchantmentLevel(Enchants.POISON))));
          event.getEntity().setMetadata(
            "PoisonLoc", 
            new FixedMetadataValue(this.plugin.plugin, 
            Integer.valueOf(item
            .getEnchantmentLevel(Enchants.POISON))));
        }
      }
    }
  }
}