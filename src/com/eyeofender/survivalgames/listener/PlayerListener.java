package com.eyeofender.survivalgames.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.events.PlayerKilledEvent;

public class PlayerListener implements Listener{

	SurvivalGames plugin;
	
	public PlayerListener ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoinGame ( PlayerJoinEvent event ){
		Player player = event.getPlayer();

		if(plugin.getGm().isInGame()){
			player.kickPlayer("PUSSY CATS");
		}
		
		event.setJoinMessage(null);
		
		/** Runs the join arena command **/
		plugin.getGm().joinArena(player);
	}
	
	@EventHandler
	public void onInteract ( PlayerInteractEvent event ){
		if(!plugin.getGm().getIsAlive().contains(event.getPlayer())){
			event.setCancelled(true);
		}
	}
	
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
    	event.setLeaveMessage(null);
    	plugin.getGm().removePlayer(event.getPlayer());
    	plugin.getGm().checkPlayers();
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
    	event.setQuitMessage(null);
    	plugin.getGm().removePlayer(event.getPlayer());
    	plugin.getGm().checkPlayers();
    }
	
	@EventHandler
	public void onPlayerDamage ( EntityDamageEvent event ){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			
			if(!plugin.getGm().isInGame()){
				event.setCancelled(true);
			}
			
			if(!plugin.getGm().getIsAlive().contains(player)){
				event.setCancelled(true);
			}
		} 
	}
	
	@EventHandler
	public void onProjectileLaunch ( ProjectileLaunchEvent event ){
		if(event.getEntity().getShooter() instanceof Player){
			Player player = (Player) event.getEntity().getShooter();
			
			if(plugin.getFrozen().contains(player)){
				event.setCancelled(true);
			}
		}
	}

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();

                if(plugin.getGm().getRampage().contains(attacked)){
                	event.setCancelled(false);
                }
                
                if(plugin.getGm().getResurrected().contains(attacker)){
                	if(plugin.getGm().findGamePlayer(attacker).getKiller() == attacked){
                		event.setCancelled(false);
                		return;
                	}
                }
                
                if(plugin.getGm().getResurrected().contains(attacked)){
                	event.setCancelled(false);
                	return;
                }
            	
            	if(!plugin.getGm().getIsAlive().contains(attacker) || plugin.getFrozen().contains(attacker)){
            		event.setCancelled(true);
            		return;
            	}
            	
                if(attacker.getItemInHand() == null){
                	event.setDamage(1);
                }
                
            	attacker.playSound(attacker.getLocation(), Sound.CREEPER_HISS, 2, 2);
            }
        }
        
        if(event.getDamager() instanceof Snowball){
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                attacked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 1));
            }
        }
        
        if(event.getDamager() instanceof Egg){
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                attacked.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 150, 1));
            }
        }
    }
	
	@EventHandler
	public void onFoodLevelChange ( FoodLevelChangeEvent event ){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			
			if(!plugin.getGm().isInGame()){
				event.setCancelled(true);
			}
			
			if(!plugin.getGm().getIsAlive().contains(player)){
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerMovement (PlayerMoveEvent event){
		Player player = event.getPlayer();
		
		if(plugin.getFrozen().contains(player)){
			if (((event.getTo().getX() != event.getFrom().getX()) || (event.getTo().getZ() != event.getFrom().getZ()))) {
				event.setTo(event.getFrom());
	            return;
	        }
		}
	}
	
	@EventHandler
	public void onPlayerRespawn ( PlayerRespawnEvent event ){
		event.setRespawnLocation(plugin.getGame().getLobby().clone().add(0.5D, 0.5D, 0.5D));
	}
	
	@EventHandler
	public void onPlayerChat ( AsyncPlayerChatEvent event ){
		Player player = event.getPlayer();
		
		if(plugin.getMuted().contains(player)){
			event.setCancelled(true);
			plugin.sendMessage(player, ChatColor.RED + "Chat is muted.");
		}
	}
	
	@EventHandler
	public void onPlayerDeath ( PlayerDeathEvent event ){
		plugin.getGm().removePlayer(event.getEntity());
		plugin.getGm().addSpectator(event.getEntity());

		plugin.getGm().checkDeathmatch();
		
		plugin.getGm().checkPlayers();
		
		if (event.getEntity().getKiller() != null) {
	        plugin.getGm().findGamePlayer(event.getEntity()).setKiller(event.getEntity().getKiller());
		}
		
		if (plugin.getGm().getResurrected().contains(event.getEntity())){
			if (event.getEntity().getKiller() == plugin.getGm().findGamePlayer(event.getEntity()).getKiller()) {
				plugin.getRc().removePlayer(event.getEntity());
				return;
			}
		}

		List<ItemStack> drops = new ArrayList<ItemStack>(event.getDrops());
		PlayerKilledEvent e = new PlayerKilledEvent(event.getEntity().getLocation(), drops, event.getEntity(), event.getEntity().getKiller(), event.getDeathMessage());
		
        Bukkit.getServer().getPluginManager().callEvent(e);
        
		plugin.getGm().findGamePlayer(event.getEntity().getKiller()).setKills(plugin.getGm().findGamePlayer(event.getEntity().getKiller()).getKills() + 1);
		
		event.setDeathMessage(ChatColor.LIGHT_PURPLE + event.getEntity().getName() + ChatColor.LIGHT_PURPLE + " has been killed. " + (plugin.getGm().getIsAlive().size()) + ChatColor.LIGHT_PURPLE +  " tributes remaining.");
	}
	
	@EventHandler
	public void onEntityDeath ( EntityDeathEvent event ){
		if(event.getEntity() instanceof Player){
			for(Player players : Bukkit.getOnlinePlayers()){
				players.playSound(players.getLocation(), Sound.ENDERDRAGON_DEATH, 2, 2);
			}
		}
	}
	
	@EventHandler
	public void onItemDrop ( PlayerDropItemEvent event ){
		Player player = event.getPlayer();
		
		if(!plugin.getGm().isInGame()){
			event.setCancelled(true);
		}
		
		if(!plugin.getGm().getIsAlive().contains(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onItemDrop ( PlayerPickupItemEvent event ){
		Player player = event.getPlayer();
		
		if(!plugin.getGm().isInGame()){
			event.setCancelled(true);
		}
		
		if(!plugin.getGm().getIsAlive().contains(player)){
			event.setCancelled(true);
		}
	}
}
