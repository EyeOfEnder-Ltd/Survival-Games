package com.eyeofender.survivalgames.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player player;

    public PlayerWinEvent(Player p) {
        player = p;
    }

    public Player getWinner() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}