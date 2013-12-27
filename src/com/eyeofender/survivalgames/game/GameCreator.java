package com.eyeofender.survivalgames.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class GameCreator {

	SurvivalGames plugin;
	
	public GameCreator ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
    public void createMap(Player player, String map) {
        String mapName = map.toLowerCase();
        String name = mapName.substring(0, 1).toUpperCase() + mapName.substring(1);

        plugin.getFh().getArena().set("name", mapName);
        plugin.getFh().getArena().set("displayname", name);
        plugin.getFh().saveArena();

        /** Sends Message **/

        player.sendMessage(ChatColor.GOLD + "You have created " + name + " successfully!");
    }
    
    public void createMap(Player player, String map1, String map2) {
    	String map = map1 + " " + map2;
        String mapName = map.toLowerCase();
        String name = mapName.substring(0, 1).toUpperCase() + mapName.substring(1);

        plugin.getFh().getArena().set("name", mapName);
        plugin.getFh().getArena().set("displayname", name);
        plugin.getFh().saveArena();

        /** Sends Message **/

        player.sendMessage(ChatColor.GOLD + "You have created " + name + " successfully!");
    }
    
    public void updatePoint1(Player player) {
        if (plugin.getFh().getArenaName() == null) {
            plugin.sendMessage(player, "Arena does not exist.  Try creating it first.");
            return;
        }

        plugin.getFh().getArena().set("point1.x", player.getLocation().getBlockX());
        plugin.getFh().getArena().set("point1.y", 0);
        plugin.getFh().getArena().set("point1.z", player.getLocation().getBlockZ());

        plugin.getFh().saveArena();

        plugin.sendMessage(player, "You have updated the current world's point 1 location successfully!");
    }
    
    public void updatePoint2(Player player) {
        if (plugin.getFh().getArenaName() == null) {
            plugin.sendMessage(player, "Arena does not exist.  Try creating it first.");
            return;
        }

        plugin.getFh().getArena().set("point2.x", player.getLocation().getBlockX());
        plugin.getFh().getArena().set("point2.y", 300);
        plugin.getFh().getArena().set("point2.z", player.getLocation().getBlockZ());

        plugin.getFh().saveArena();

        plugin.sendMessage(player, "You have updated the current world's point 2 location successfully!");
    }
    
    public void updateLobby(Player player) {
        plugin.getConfig().set("lobby.world", player.getWorld().getName());
        plugin.getConfig().set("lobby.x", player.getLocation().getBlockX());
        plugin.getConfig().set("lobby.y", player.getLocation().getBlockY());
        plugin.getConfig().set("lobby.z", player.getLocation().getBlockZ());
        plugin.getConfig().set("lobby.yaw", player.getLocation().getYaw());
        plugin.getConfig().set("lobby.pitch", player.getLocation().getPitch());

        plugin.saveConfig();

        player.sendMessage(ChatColor.GOLD + "You have updated the current world's lobby successfully!");
    }
    
    public void updateArenaWorld(Player player) {
        if (plugin.getFh().getArenaName() == null) {
            player.sendMessage(ChatColor.RED + "Arena does not exist. Try creating it first.");
            return;
        }

        plugin.getFh().getArena().set("world", player.getWorld().getName());

        plugin.getFh().saveArena();

        player.sendMessage(ChatColor.GOLD + "You have updated the current world's world successfully!");
    }
    
    public void updateSpawns(Player player){
        if (plugin.getFh().getArenaName() == null) {
            player.sendMessage(ChatColor.RED + "Arena does not exist. Try creating it first.");
            return;
        }    	
   	 	
        int spawns = 0;
   	 			
        if(plugin.getFh().getArena().contains("spawns")){
        	spawns = plugin.getFh().getArena().getInt("spawns");
        	spawns++;
        }
        
        if(spawns >= plugin.getConfigHandler().getMaxPlayers()){
        	player.sendMessage(ChatColor.RED + "Error placing spawn point. Too many spawns.");
        }
   	            
        plugin.getFh().getArena().set("spawn" + spawns + ".x", player.getLocation().getBlockX());
        plugin.getFh().getArena().set("spawn" + spawns + ".y", player.getLocation().getBlockY());
        plugin.getFh().getArena().set("spawn" + spawns + ".z", player.getLocation().getBlockZ());
        plugin.getFh().getArena().set("spawn" + spawns + ".yaw", player.getLocation().getYaw());
        plugin.getFh().getArena().set("spawn" + spawns + ".pitch", player.getLocation().getPitch());

   	    plugin.getFh().getArena().set("spawns", spawns);

	    plugin.getFh().saveArena();

	    player.sendMessage(ChatColor.GOLD + "You have updated the current world's " + spawns + " spawn point successfully!");
   }
    
    public void updateDeathMatchSpawns(Player player){
        if (plugin.getFh().getArenaName() == null) {
            player.sendMessage(ChatColor.RED + "Arena does not exist. Try creating it first.");
            return;
        }    	
   	 	
        int spawns = 0;
   	 			
        if(plugin.getFh().getArena().contains("dmspawns")){
        	spawns = plugin.getFh().getArena().getInt("dmspawns");
        	spawns++;
        }
        
        if(spawns >= plugin.getConfigHandler().getDeathmatchPlayers())
        	player.sendMessage(ChatColor.RED + "Error placing spawn point. Too many spawns.");
        
   	            
        plugin.getFh().getArena().set("dmspawn" + spawns + ".x", player.getLocation().getBlockX());
        plugin.getFh().getArena().set("dmspawn" + spawns + ".y", player.getLocation().getBlockY());
        plugin.getFh().getArena().set("dmspawn" + spawns + ".z", player.getLocation().getBlockZ());
        plugin.getFh().getArena().set("dmspawn" + spawns + ".yaw", player.getLocation().getYaw());
        plugin.getFh().getArena().set("dmspawn" + spawns + ".pitch", player.getLocation().getPitch());

   	    plugin.getFh().getArena().set("dmspawns", spawns);

	    plugin.getFh().saveArena();

	    player.sendMessage(ChatColor.GOLD + "You have updated the current world's " + spawns + "  death match spawn point successfully!");
   }  
    
    public void updateFirework(Player player){
   	 if (plugin.getFh().getArenaName() == null) {
            player.sendMessage(ChatColor.RED + "Arena does not exist.  Try creating it first.");
            return;
        }
   	 			int randoms = 0;
   	 			if(plugin.getFh().getArena().contains("fireworks")){
   	 				randoms = plugin.getFh().getArena().getInt("fireworks");
       	            randoms++;
   	 			}

   	            plugin.getFh().getArena().set("firework" + randoms + ".x", player.getLocation().getBlockX());
   	            plugin.getFh().getArena().set("firework" + randoms + ".y", player.getLocation().getBlockY());
   	            plugin.getFh().getArena().set("firework" + randoms + ".z", player.getLocation().getBlockZ());

   	            plugin.getFh().getArena().set("fireworks", randoms);

   	            plugin.getFh().saveArena();

   	            player.sendMessage(ChatColor.GOLD + "You have updated the current world's firework " + randoms + " spawn point successfully!");
   } 
}
