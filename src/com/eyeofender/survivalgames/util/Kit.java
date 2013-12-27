package com.eyeofender.survivalgames.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import com.eyeofender.survivalgames.handlers.KitHandler;

public class Kit
{
	  private String kitName;
	  private ItemStack[] armor;
	  private ItemStack[] items;
	  private List<String> players = new ArrayList<String>();
	  private String defaultPermission;
	  private List<String> permission;
	  private String description = "No description was provided for this kit.";
	  private String itemDescription = "No items have been added to the description.";
	  private String[] abilities;
	  private List<PotionEffect> effects;
	  public static KitHandler main;
	  private String perk;
	  private int cost;
	  private boolean vip;
	
	  public Kit(String name, ItemStack[] armour, ItemStack[] item, List<String> perm, String Defaultperm, String desc, String items, String[] abilitys, List<PotionEffect> effects, String Perk, int cost, boolean vip)
	  {
	    this.kitName = name;
	    this.armor = armour;
	    this.items = item;
	    this.permission = perm;
	    if (desc != null)
	      this.description = desc;
	    this.itemDescription = items;
	    this.defaultPermission = Defaultperm;
	    this.abilities = abilitys;
	    this.effects = effects;
	    this.perk = Perk;
	    this.setCost(cost);
	    this.setVip(vip);
	  }
	
	  public String getDescription() {
	    return this.description;
	  }
	
	  public int getAmount() {
	    return this.players.size();
	  }
	
	  public boolean hasAbility(String string) {
	    for (String ability : this.abilities)
	      if (string.equalsIgnoreCase(ability))
	        return true;
	    return false;
	  }
	
	  public List<String> getPlayers() {
	    return this.players;
	  }
	
	  public List<String> getPermission() {
	    return this.permission;
	  }
	
	  public void addPlayer(String player) {
	    if (!this.players.contains(player))
	      this.players.add(player);
	  }
	
	  public void removePlayer(String player) {
	    this.players.remove(player);
	  }
	
	  public String getName() {
	    return this.kitName;
	  }
	
	  public String getSafeName() {
	    return ChatColor.stripColor(this.kitName);
	  }
	
	  public ItemStack[] getArmor() {
	    return this.armor;
	  }
	
	  public ItemStack[] getItems() {
	    return this.items;
	  }
	
	  public void giveKit() {
	    double time = 0.0D;
	    for (final String player : this.players) {
	      Player p = Bukkit.getPlayerExact(player);
	      if (p != null)
	      {
	        time += 0.1D;
	        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
	          public void run() {
	            Player p = Bukkit.getPlayerExact(player);
	            if (p == null)
	              return;
	            if(!main.plugin.getGm().getIsAlive().contains(p))
	            	return;
	            PlayerInventory inv = p.getInventory();
	            ItemStack[] arm = inv.getArmorContents();
	            for (int n = 0; n < 4; n++)
	              if ((Kit.this.armor[n] != null) && (Kit.this.armor[n].getType() != Material.AIR))
	              {
	                if ((arm[n] == null) || (arm[n].getType() == Material.AIR))
	                  arm[n] = Kit.this.armor[n].clone();
	                else
	                  inv.addItem(new ItemStack[] { Kit.this.armor[n].clone() });
	              }
	            inv.setArmorContents(arm);
	            for (ItemStack item : Kit.this.items)
	              inv.addItem(new ItemStack[] { item.clone() });
	            for(PotionEffect e : effects){
	            	p.addPotionEffect(e);
	            }
	            p.updateInventory();
	          }
	        }
	        , Math.round(Math.floor(time)));
	      }
	    }
	  }
	
	public String getPerk() {
		return perk;
	}
	
	public void setPerk(String perk) {
		this.perk = perk;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	
	public String getDefaultPermission() {
		return defaultPermission;
	}
	
	public void setDefaultPermission(String defaultPermission) {
		this.defaultPermission = defaultPermission;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}
}