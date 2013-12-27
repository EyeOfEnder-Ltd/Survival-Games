package com.eyeofender.survivalgames.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.eyeofender.survivalgames.SurvivalGames;

public class Game {

	SurvivalGames plugin;
	
	private Location lobby;
	private List<Location> spawns = new ArrayList<Location>();
	private List<Location> dmspawns = new ArrayList<Location>();
	public List<Location> getDmspawns() {
		return dmspawns;
	}

	public void setDmspawns(List<Location> dmspawns) {
		this.dmspawns = dmspawns;
	}

	private World world;
	
	public Game ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void loadLobby(){
		World world = Bukkit.getWorld(plugin.getConfig().getString("lobby.world"));

		if(world != null){
			String y = plugin.getConfig().getString("lobby.yaw");
	        String p = plugin.getConfig().getString("lobby.pitch");
	        
	
	        float yaw = Float.parseFloat(y);
	        float pitch = Float.parseFloat(p);
	        
			lobby = new Location(world, plugin.getConfig().getInt("lobby.x"), plugin.getConfig().getInt("lobby.y"), plugin.getConfig().getInt("lobby.z"), yaw, pitch);
		}
	}
	
	public void loadSpawns(){
		plugin.getServer().createWorld(new WorldCreator(plugin.getConfig().getString("world")));
		World world = plugin.getServer().getWorld(plugin.getConfig().getString("world"));

		if(world != null){
			this.setWorld(world);
            int randomFireworks = plugin.getFh().getArena().getInt("spawns");
            for(int i = 0; i < randomFireworks; i++){
    	        String y = plugin.getFh().getArena().getString("spawn" + i + ".yaw");
    	        String p = plugin.getFh().getArena().getString("spawn" + i + ".pitch");
    	
    	        float yaw = Float.parseFloat(y);
    	        float pitch = Float.parseFloat(p);
    	        
            	spawns.add(new Location(world, plugin.getFh().getArena().getInt("spawn" + i + ".x"), plugin.getFh().getArena().getInt("spawn" + i + ".y"), plugin.getFh().getArena().getInt("spawn" + i + ".z"), yaw, pitch ).clone().add(.5D,.5D,.5D));
            }
		}
		
		if(world != null){
            int randomFireworks = plugin.getFh().getArena().getInt("dmspawns");
            for(int i = 0; i < randomFireworks; i++){
    	        String y = plugin.getFh().getArena().getString("dmspawn" + i + ".yaw");
    	        String p = plugin.getFh().getArena().getString("dmspawn" + i + ".pitch");
    	
    	        float yaw = Float.parseFloat(y);
    	        float pitch = Float.parseFloat(p);
    	        
            	dmspawns.add(new Location(world, plugin.getFh().getArena().getInt("dmspawn" + i + ".x"), plugin.getFh().getArena().getInt("dmspawn" + i + ".y"), plugin.getFh().getArena().getInt("dmspawn" + i + ".z"), yaw, pitch ).clone().add(.5D,.5D,.5D));
            }
		}
	}
	
	/**********************************************
	 * 
	 * 				Getter & Setter
	 * 
	 *********************************************/

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}
	
	public List<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
