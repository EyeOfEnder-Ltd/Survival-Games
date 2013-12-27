package com.eyeofender.survivalgames.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.eyeofender.survivalgames.SurvivalGames;

public class VoteHandler {

	HashMap<String, Integer> votes = new HashMap<String, Integer>();
	List<Player> playersVoted = new ArrayList<Player>();
	List<String> maps = new ArrayList<String>();
	private String map = null;
	
	SurvivalGames plugin;
	
	public VoteHandler ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
		for(String s : plugin.getConfig().getStringList("maps")){
			String map = s.toLowerCase();
			maps.add(map);
		}
		
		for(String s : maps){
			votes.put(s, 0);
		}
		
		votes.put(maps.get(0), 1);
	}
	
	public void vote(Player player, String mapNames){
		String map = mapNames.toLowerCase();
			if(playersVoted.contains(player)){
				plugin.sendMessage(player, ChatColor.RED + "You have already voted!");
				return;
			}
			
			if(!maps.contains(map)){
				plugin.sendMessage(player, ChatColor.RED + "Map not found!");
				return;
			}
		
			String mapName = map;
			int numVotes = votes.get(mapName);
			votes.put(mapName, numVotes + 1);
			
			if(plugin.getEp().getRankManager().hasRank(player)){
				int numVotess = votes.get(mapName);
				votes.put(mapName, numVotess + 1);
				plugin.sendMessage(player, ChatColor.GOLD + "You have double voted for " + map + "!");
			}else{
				plugin.sendMessage(player, "You have voted for " + map + "!");
			}
			playersVoted.add(player);
	}
	
	public void vote(Player player, String mapNames1, String mapNames2){
		String mapNames = mapNames1 + " " + mapNames2;
		String map = mapNames.toLowerCase();
			if(playersVoted.contains(player)){
				plugin.sendMessage(player, ChatColor.RED + "You have already voted!");
				return;
			}
			
			if(!maps.contains(map)){
				plugin.sendMessage(player, ChatColor.RED + "Map not found!");
				return;
			}
		
			String mapName = map;
			int numVotes = votes.get(mapName);
			votes.put(mapName, numVotes + 1);
			playersVoted.add(player);
			plugin.sendMessage(player, "You have voted for " + map + "!");
	}
	
	public void vote(Player player, int mapInt){
		if(!(maps.size() >= mapInt)){
			plugin.sendMessage(player, ChatColor.RED + "Map not found!");
		}
		
			if(playersVoted.contains(player)){
				plugin.sendMessage(player, ChatColor.RED + "You have already voted!");
				return;
			}
			
			if(!maps.contains(map)){
				plugin.sendMessage(player, ChatColor.RED + "Map not found!");
				return;
			}
		
			String mapNames = maps.get(mapInt);
			String map = mapNames.toLowerCase();

			String mapName = map;
			int numVotes = votes.get(mapName);
			votes.put(mapName, numVotes + 1);
			playersVoted.add(player);
			plugin.sendMessage(player, "You have voted for " + map + "!");
			
			String mapNamess = Character.toUpperCase(mapName.charAt(0)) + mapName.substring(1);
			for(Player players : Bukkit.getServer().getOnlinePlayers()){
				players.sendMessage(ChatColor.BLUE + player.getName() + ChatColor.GRAY + " has voted for " + ChatColor.AQUA + mapNamess + "!");
			}
	}
	
	public void listMaps(){
		for(Player player : plugin.getServer().getOnlinePlayers()){
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- Voting -=-=-=- -=-=- -=-");

			for(String map: maps){
				String mapName = Character.toUpperCase(map.charAt(0)) + map.substring(1);
	        	int votesNum = votes.get(map);
				player.sendMessage(ChatColor.AQUA + "Do " + ChatColor.AQUA + "/Vote " + mapName + ChatColor.GRAY + ": To vote for " + mapName + " - " + votesNum + " votes.");
			}
			
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- Voting -=-=-=- -=-=- -=-");
		}
	}
	
	public void listMap(Player player){
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- Voting -=-=-=- -=-=- -=-");
	
			for(String map: maps){
				String mapName = Character.toUpperCase(map.charAt(0)) + map.substring(1);
	        	int votesNum = votes.get(map);
				player.sendMessage(ChatColor.AQUA + "Do " + ChatColor.AQUA + "/Vote " + mapName + ChatColor.GRAY + ": To vote for " + mapName + " - " + votesNum + " votes.");
			}
			
			player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- Voting -=-=-=- -=-=- -=-");
	
	}
	
	public void loadMap(){
		int highestValue = 0;
        String votedMap = "";
        
        for(String s : maps){
        	int votesNum = votes.get(s);
        	if(votesNum > highestValue){
        		highestValue = votesNum;
        		votedMap = s;
        	}
        }
        
        this.setMap(votedMap);
        plugin.getConfig().set("load", votedMap);
        plugin.getWh().refreshWorld(plugin.getConfig().getString("world"), plugin.getConfig().getString("load"));
	}
	
	public void teleportAll(){
		plugin.getServer().createWorld(new WorldCreator(plugin.getConfig().getString("world")));
		World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("world"));
		if(world == null)
			plugin.getLogger().info("World not found");
		
		Location l = world.getSpawnLocation();
        
        for(Player player : plugin.getServer().getOnlinePlayers()){
        	player.teleport(l);
        }
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}
