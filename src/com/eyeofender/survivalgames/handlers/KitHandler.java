package com.eyeofender.survivalgames.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.UniversalCredits.UniversalCredits;
import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.util.Enchants;
import com.eyeofender.survivalgames.util.Kit;

public class KitHandler	implements Listener
	{
		
	  private List<Kit> kits = new ArrayList<Kit>();

	public SurvivalGames plugin;
	  String defaultKit;
	  
	  public KitHandler ( SurvivalGames plugin ){
		  this.plugin = plugin;
	  }

	  public void init()
	  {
	    Kit.main = this;
	    new Enchants();

	    PluginManager manager = plugin.getServer().getPluginManager();
	    manager.registerEvents(this, plugin);
	    plugin.saveDefaultConfig();
	    this.defaultKit = plugin.getConfig().getString("DefaultKit");
	    for (String string : plugin.getConfig().getConfigurationSection("Kits").getKeys(
	      false))
	    {
	      Kit kit = parseKit(plugin.getConfig().getConfigurationSection("Kits." + string));
	      this.kits.add(kit);
	    }
	  }

	  Kit parseKit(ConfigurationSection path) {
		String desc = ChatColor.translateAlternateColorCodes('&', path.getString("Description"));
	    String itemDesc = ChatColor.translateAlternateColorCodes('&', path.getString("ItemDescription"));
	    List<String> perm = path.getStringList("Permission");
	    String defaultPerm = path.getString("DefaultPermission");
	    String name = path.getString("Name");
	    if (name == null)
	      name = path.getName();
	    name = ChatColor.translateAlternateColorCodes('&', name);
	    ItemStack[] armor = new ItemStack[4];
	    armor[3] = parseItem(path.getString("Helmet"))[0];
	    armor[2] = parseItem(path.getString("Chestplate"))[0];
	    armor[1] = parseItem(path.getString("Leggings"))[0];
	    armor[0] = parseItem(path.getString("Boots"))[0];
	    List itemList = path.getStringList("Items");
	    List<String> potion = path.getStringList("Potion");
	    String perks = path.getString("Perk");
	    int cost = path.getInt("Cost");
	    boolean vip = path.getBoolean("Vip");
	    ArrayList item = new ArrayList();
	    if (itemList != null)
	    {
	      for (Iterator localIterator = itemList.iterator(); localIterator.hasNext(); )
	      {
	        String string = (String)localIterator.next();
	        ItemStack[] itemstacks = parseItem(string);
	        ItemStack[] arrayOfItemStack1;
	        int j = (arrayOfItemStack1 = itemstacks).length; int i = 0;
	        ItemStack itemstack = arrayOfItemStack1[i];
	        if (itemstack != null)
	          item.add(itemstack);
	        i++;
	      }
	    }

	    ItemStack[] items = new ItemStack[item.size()];
	    for (int n = 0; n < item.size(); n++)
	      items[n] = ((ItemStack)item.get(n));
	    List abilityList = path.getStringList("Ability");
	    String[] ability;
	    if (abilityList != null) {
	      ability = new String[abilityList.size()];
	      for (int n = 0; n < abilityList.size(); n++)
	        ability[n] = ((String)abilityList.get(n));
	    } else {
	      ability = new String[0];
	    }
	    
	    List<PotionEffect> potions = new ArrayList<PotionEffect>();
	    
	    for(String s : potion){
	    	if(s.equalsIgnoreCase("Inv")){
	    		potions.add(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 2));
	    	}
	    }
	    	
	    Kit kit = new Kit(name, armor, items, perm, defaultPerm, desc ,itemDesc, ability, potions, perks, cost, vip);
	    return kit;
	  }

	  public boolean hasAbility(Player p, String ability) {
	    Kit kit = getKitByPlayer(p.getName());
	    if ((kit == null) || (!kit.hasAbility(ability)))
	      return false;
	    return true;
	  }
	  
	  public boolean hasPerk(Player p, String Perk) {
		    Kit kit = getKitByPlayer(p.getName());
		    if ((kit == null) || (!kit.getPerk().equalsIgnoreCase(Perk)))
		      return false;
		    
		    return true;
		  }

	  ItemStack[] parseItem(String string) {
	    if (string == null)
	      return new ItemStack[1];
	    String[] args = string.split(" ");
	    try {
	      double amount = Integer.parseInt(args[2]);
	      ItemStack[] items = new ItemStack[(int)Math.ceil(amount / 64.0D)];
	      if (items.length <= 0)
	        return new ItemStack[1];
	      for (int i = 0; i < items.length; i++) {
	        ItemStack item = new ItemStack(Integer.parseInt(args[0]), (int)amount, 
	          (short)Integer.parseInt(args[1]));
	        int cur = 3;
	        while (args.length > cur + 1) {
	          item.addUnsafeEnchantment(Enchantment.getByName(args[cur]), 
	            Integer.parseInt(args[(cur + 1)]));
	          cur += 2;
	        }
	        item = Enchants.updateEnchants(item);
	        amount -= 64.0D;
	        items[i] = item;
	      }
	      return items;
	    } catch (NumberFormatException nfe) {
	    	plugin.getLogger().log(Level.SEVERE, 
	        "Error with string " + StringUtils.join(args, " "));
	    	plugin.getLogger().log(Level.SEVERE, nfe.getMessage());
	    }
	    return new ItemStack[1];
	  }

	 /** @EventHandler(priority = EventPriority.HIGHEST)
	  public void onPlayerJoin(PlayerJoinEvent event){
		  Player p = event.getPlayer();
		  Kit kit = getKitByName(defaultKit);
	      if (kit == null) {
	        p.sendMessage(ChatColor.RED + "This kit does not exist!");
	        p.sendMessage(ChatColor.RED + 
	          "Type /kit for all the kits you can use!");
	      }
	      List hisKits = hisKits(p);
	      if (hisKits.contains(kit)) {
	        Kit kito = getKitByPlayer(p.getName());
	        if (kito != null)
	          kito.removePlayer(p.getName());
	        kit.addPlayer(p.getName());
	        p.sendMessage(ChatColor.RED + "Now using kit " + kit.getName() + 
	          ChatColor.RED + "!");
	      } else {
	        p.sendMessage(ChatColor.RED + "You do not own this kit!");
	      }
	  }**/
	  
	  public Kit getKitByName(String name)
	  {
	    for (Kit kit : this.kits)
	      if (ChatColor.stripColor(kit.getName()).equalsIgnoreCase(name))
	        return kit;
	    return null;
	  }

	  public Kit getKitByPlayer(String name) {
	    for (Kit kit : this.kits)
	      if (kit.getPlayers().contains(name))
	        return kit;
	    return null;
	  }

	  void showKits(Player p) {
	    List hisKits = new ArrayList();
	    List otherKits = new ArrayList();
	    for (Kit kit : hisKits(p))
	      hisKits.add(kit.getName());
	    for (Kit kit : otherKits(p))
	      otherKits.add(kit.getName());
	    Collections.sort(hisKits, String.CASE_INSENSITIVE_ORDER);
	    Collections.sort(otherKits, String.CASE_INSENSITIVE_ORDER);
	    if (getKitByPlayer(p.getName()) != null)
	      p.sendMessage(ChatColor.RED + "Your current kit: " + ChatColor.GREEN + 
	        getKitByPlayer(p.getName()).getName());
	    else
	      p.sendMessage(ChatColor.RED + "Your current kit: " + ChatColor.GREEN + 
	        "None");
	    if (hisKits.size() == 0) {
	      p.sendMessage(ChatColor.RED + "Your kits: " + ChatColor.GREEN + 
	        "No kits available..");
	    } else {
	      String list = StringUtils.join(hisKits, "" + ChatColor.RESET + ChatColor.GREEN + ", ");
	      p.sendMessage(ChatColor.RED + "Your kits: " + ChatColor.GREEN + list + 
	        ChatColor.GREEN + ".");
	    }
	    if (otherKits.size() == 0) {
	      p.sendMessage(ChatColor.RED + "Other kits: " + ChatColor.GREEN + 
	        "No kits available..");
	    } else {
	      String list = StringUtils.join(otherKits,"" + ChatColor.RESET + ChatColor.GREEN + ", ");
	      p.sendMessage(ChatColor.RED + "Other kits: " + ChatColor.GREEN + list + 
	        ChatColor.GREEN + ".");
	    }
	  }

	  public List<Kit> hisKits(Player p) {
	    List<Kit> hisKit = new ArrayList<Kit>();
	    
	    for (Kit kit : this.kits) {
	    	 for(String perms : kit.getPermission()){
				  if((p.hasPermission(perms) || p.isOp()) && !hisKit.contains(kit)){
					  hisKit.add(kit);
				  }
	    	 }
	    }
	    
	    return hisKit;
	  }
	  
	  public boolean hasKit(Player player, String Kit){
		  Kit kit = getKitByName(Kit);
		  for(String perms : kit.getPermission()){
			  if(player.hasPermission(perms) || player.isOp()){
				  return true;
			  }
		  }
		  
		  return false;
	  }

	  public List<Kit> otherKits(Player p) {
		  List<Kit> hisKit = new ArrayList<Kit>();
		  for (Kit kit : this.kits) {
	    		 for(String perms : kit.getPermission()){
	    			  if(!p.hasPermission(perms) && !hisKit.contains(kit))
				  			hisKit.add(kit);
	    		  }
	    }
	    return hisKit;
	  }

	  String getDescription(String name) {
	    Kit kit = getKitByName(name);
	    if (kit == null)
	      return ChatColor.RED + "This kit does not exist!";
	    return kit.getDescription();
	  }
	  
	  Integer getCost(String name){
		  Kit kit = getKitByName(name);
		  if(kit != null){
			  return kit.getCost();
		  }
		return null;
	  }

	  public void addPermission(String player, String permissionNode)
	  {
	      ru.tehkode.permissions.bukkit.PermissionsEx.getUser(player).addPermission(permissionNode);
	  }
	   
	  public void removePermission(String player, String permissionNode)
	  {
	      ru.tehkode.permissions.bukkit.PermissionsEx.getUser(player).removePermission(permissionNode);
	  }
	  
	  public void buyKit(Player player, String Name){
		  Kit kit = getKitByName(Name);
		  
		  if(kit != null){
			  if(!this.hasKit(player, kit.getName()) || kit.getCost() == 0){
				  if(!kit.isVip()){
					  if(UniversalCredits.api.canAfford(player, kit.getCost() - 1)){
						  try{
							  UniversalCredits.api.withdraw(player.getName(), kit.getCost());
							  addPermission(player.getName(), kit.getDefaultPermission());
							  plugin.sendMessage(player, ChatColor.GOLD + "You have bought kit: " + Name + ChatColor.BOLD + "!");
						  }catch(Exception e){
							  plugin.sendMessage(player, "Failed to buy kit. " + kit.getName());
						  }
				  		}else
				  			plugin.sendMessage(player,ChatColor.RED + "You can not afford this kit.");
				  }else
					  plugin.sendMessage(player,ChatColor.RED + "You have to be vip to use this kit.");
			  }else
				  plugin.sendMessage(player,ChatColor.RED + "You already own this kit.");
		  }else{
				plugin.sendMessage(player, ChatColor.RED + "Kit not found! Do /Kits to list your kits.");
		  }
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	  {
	    Player p = Bukkit.getPlayerExact(sender.getName());
	    if (p == null) {
	      sender.sendMessage("Sorry but you must be in the game");
	      return true;
	    }
	    
	    if(cmd.getName().equalsIgnoreCase("buykit")){
	    	if(args.length > 0){
	    		buyKit(p, args[0]);
	    	}else
	            p.sendMessage(ChatColor.RED + "You need to define a kit name!");
	    }
	    return true;
	  }
	  
	  public List<Kit> getKits() {
		return kits;
	}

	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}
}
