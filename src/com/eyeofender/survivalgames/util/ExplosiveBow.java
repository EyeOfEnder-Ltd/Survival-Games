package com.eyeofender.survivalgames.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class ExplosiveBow extends Enchantment
{
  public ExplosiveBow(int id)
  {
    super(id);
  }

  public boolean canEnchantItem(ItemStack item)
  {
    return item.getType() == Material.BOW;
  }

  public boolean conflictsWith(Enchantment other)
  {
    return false;
  }

  public EnchantmentTarget getItemTarget()
  {
    return EnchantmentTarget.BOW;
  }

  public int getMaxLevel()
  {
    return 10;
  }

  public String getName()
  {
    return "Explosive";
  }

  public int getStartLevel()
  {
    return 1;
  }
}