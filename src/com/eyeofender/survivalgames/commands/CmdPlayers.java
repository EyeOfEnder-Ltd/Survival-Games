package com.eyeofender.survivalgames.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.eyeofender.survivalgames.SurvivalGames;

public class CmdPlayers implements CommandExecutor{

	SurvivalGames plugin;
	
	public CmdPlayers ( SurvivalGames plugin ){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

		if(!(sender instanceof Player)){
			plugin.getLogger().info("You can not execute this command while not in game.");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("players") || CommandLabel.equalsIgnoreCase("list")){
				if(args.length >= 0){
					if(!plugin.getGm().isInGame()){
						player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
						player.sendMessage(ChatColor.YELLOW+"             In Lobby: (" + Bukkit.getOnlinePlayers().length + ChatColor.YELLOW + "/" + (plugin.getConfigHandler().getMaxPlayers()) +  ChatColor.YELLOW  + ")                 ");

						List<String> owner = new ArrayList<String>();
						List<String> dev = new ArrayList<String>();
						List<String> admin = new ArrayList<String>();
						List<String> mod = new ArrayList<String>();
						List<String> vip = new ArrayList<String>();
						List<String> tribute = new ArrayList<String>();

						for(Player players : Bukkit.getOnlinePlayers()){
							String pn = players.getName();
							
							if(hasPermission(players,"eoe.rank.owner")){
								owner.add(pn);
							}
							
							else if(hasPermission(players,"eoe.rank.dev")){
								dev.add(pn);
							}
							
							else if(hasPermission(players,"eoe.rank.admin")){
								admin.add(pn);
							}
							
							else if(hasPermission(players,"eoe.rank.mod")){
								mod.add(pn);
							}
							
							else if(hasPermission(players,"eoe.rank.vip") || hasPermission(players,"sg.rank.vip")){
								vip.add(pn);
							}
							
							else{
								tribute.add(pn);
							}
						}
							
						if(owner.size() != 0){
					      String owners = StringUtils.join(owner, "" + ChatColor.RESET+ ChatColor.GRAY + ", ");
					      player.sendMessage(ChatColor.DARK_RED + "Owner: " + owners + ChatColor.GRAY + ".");
						}
						
						if(admin.size() != 0){
						  String admins = StringUtils.join(admin, "" + ChatColor.RESET + ChatColor.GRAY + ", ");
						  player.sendMessage(ChatColor.RED + "Admin: " + admins + ChatColor.GRAY + ".");
						}
						
						if(dev.size() != 0){
					      String devs = StringUtils.join(dev, "" + ChatColor.RESET+ ChatColor.GRAY + ", ");
					      player.sendMessage(ChatColor.LIGHT_PURPLE + "Dev: " + devs + ChatColor.GRAY + ".");
						}
						
						if(mod.size() != 0){
						  String mods = StringUtils.join(mod, "" + ChatColor.RESET + ChatColor.GRAY+ ", ");
						  player.sendMessage(ChatColor.RED + "Mod: " + mods + ChatColor.GRAY + ".");
						}
						
						if(vip.size() != 0){
						  String vips = StringUtils.join(vip, "" + ChatColor.RESET + ChatColor.GRAY+ ", ");
						  player.sendMessage(ChatColor.GOLD + "Vip: " + vips + ChatColor.GRAY + ".");
						}
						
						if(tribute.size() != 0){
						      String tribues = StringUtils.join(tribute, "" + ChatColor.RESET+ ChatColor.GRAY + ", ");
						      player.sendMessage(ChatColor.GRAY + "Tributes: " + tribues + ChatColor.GRAY + ".");
						}

						player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
					}else{
						for(Player players : Bukkit.getOnlinePlayers()){
							player.sendMessage(ChatColor.GOLD + "-=- -=-=- -=-=-=- -=-=-=-=- -=-=-=- -=-=- -=-");
							player.sendMessage(ChatColor.YELLOW+"             Alive: (" + plugin.getGm().getIsAlive().size() + 1 + ChatColor.YELLOW + "/" + Bukkit.getOnlinePlayers().length +  ChatColor.YELLOW  + ")                 ");

							if(plugin.getGm().getIsAlive().contains(players)){
								List<String> alive = new ArrayList<String>();
							
								String pn = players.getName();
								
								if(hasPermission(players,"eoe.rank.owner")){
									alive.add(ChatColor.DARK_RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.dev")){
									alive.add(ChatColor.LIGHT_PURPLE + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.admin")){
									alive.add(ChatColor.RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.mod")){
									alive.add(ChatColor.RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.vip") || hasPermission(players,"sg.rank.vip")){
									alive.add(ChatColor.GOLD + pn);
								}
								
								else{
									alive.add(ChatColor.GRAY + pn);
								}
							      String aliveP = StringUtils.join(alive, "" + ChatColor.RESET+ ChatColor.GRAY + ", ");
	
								player.sendMessage(ChatColor.GREEN + "Alive players: " + aliveP + ChatColor.GRAY + ".");
							}else{
								List<String> alive = new ArrayList<String>();
								
								String pn = players.getName();
								
								if(hasPermission(players,"eoe.rank.owner")){
									alive.add(ChatColor.DARK_RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.dev")){
									alive.add(ChatColor.LIGHT_PURPLE + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.admin")){
									alive.add(ChatColor.RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.mod")){
									alive.add(ChatColor.RED + pn);
								}
								
								else if(hasPermission(players,"eoe.rank.vip") || hasPermission(players,"sg.rank.vip")){
									alive.add(ChatColor.GOLD + pn);
								}
								
								else{
									alive.add(ChatColor.GRAY + pn);
								}
							      String aliveP = StringUtils.join(alive, "" + ChatColor.RESET+ ChatColor.GRAY + ", ");
	
								player.sendMessage(ChatColor.RED + "Dead Players: " + aliveP + ChatColor.GRAY + ".");
							}
						}
					}
				}else{
					player.sendMessage(ChatColor.RED + "Too many arguments.");
				}
		}
		
		return false;
	}
	
	boolean hasPermission(Player player, String permission)
    {
        Permission p = new Permission(permission, PermissionDefault.TRUE);
        return player.hasPermission(p);
    }
	
	
}
