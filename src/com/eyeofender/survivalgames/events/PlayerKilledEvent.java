package com.eyeofender.survivalgames.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerKilledEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Location location;
    private boolean cancelled = false;
    private List<ItemStack> drops;
    private Player killer;
    private Player killed;
    private String deathMessage;

    public PlayerKilledEvent(Location loc, List<ItemStack> drop, Player murderer, Player murdered, String reason) {
        location = loc;
        drops = drop;
        killer = murderer;
        killed = murdered;
        deathMessage = reason;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public Player getKiller() {
        return killer;
    }

    public Player getKilled() {
        return killed;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public void setCancelled(Boolean cancel) {
        cancelled = cancel;
    }

    public void setDrops(List<ItemStack> drop) {
        drops = drop;
    }

    public void setKiller(Player player) {
        killer = player;
    }

    public void setKilled(Player player) {
        killed = player;
    }

    public void setDeathMessage(String message) {
        deathMessage = message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
