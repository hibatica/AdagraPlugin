package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.AdagraPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelStopEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private Player player;
    public DuelStopEvent(Player playerRequestingStop) {
        this.player = playerRequestingStop;
    }

    public Player getPlayer() {
        return player;
    }
}
