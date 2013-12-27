package com.eyeofender.survivalgames.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchants
{
  private static List<Integer> customEnchants = new ArrayList();
  public static final Enchantment EXPLOSIVE = new ExplosiveBow(10);
  public static final Enchantment POISON = new PoisonBow(11);

  private static final String[] RCODE = { "M", "CM", "D", "CD", "C", "XC", "L", 
    "XL", "X", "IX", "V", "IV", "I" };

  private static final int[] BVAL = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 
    9, 5, 4, 1 };

  public Enchants()
  {
    try
    {
      Field field = Enchantment.class.getDeclaredField("acceptingNew");
      field.setAccessible(true);
      field.setBoolean(EXPLOSIVE, true);
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    if (Enchantment.getById(EXPLOSIVE.getId()) == null) {
      Enchantment.registerEnchantment(EXPLOSIVE);
      customEnchants.add(Integer.valueOf(EXPLOSIVE.getId()));
      Enchantment.registerEnchantment(POISON);
      customEnchants.add(Integer.valueOf(POISON.getId()));
    }
  }

  public static ItemStack updateEnchants(ItemStack item) {
    ArrayList enchants = new ArrayList();
    for (Enchantment ench : item.getEnchantments().keySet()) {
      if (!isNatural(ench)) {
        enchants.add(ChatColor.GRAY + ench.getName() + " " + 
          toRoman(((Integer)item.getEnchantments().get(ench)).intValue()));
      }
    }
    ItemMeta meta = item.getItemMeta();
    try {
		meta.setLore(enchants);
	} catch (Exception e) {
		e.printStackTrace();
	}
    item.setItemMeta(meta);
    return item;
  }

  private static boolean isNatural(Enchantment ench) {
    if (customEnchants.contains(Integer.valueOf(ench.getId())))
      return false;
    return true;
  }

  private static String toRoman(int binary)
  {
    if ((binary <= 0) || (binary >= 4000)) {
      throw new NumberFormatException("Value outside roman numeral range.");
    }
    String roman = "";
    for (int i = 0; i < RCODE.length; i++) {
      while (binary >= BVAL[i]) {
        binary -= BVAL[i];
        roman = roman + RCODE[i];
      }
    }
    return roman;
  }
}