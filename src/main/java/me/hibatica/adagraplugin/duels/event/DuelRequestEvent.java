package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.AdagraPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelRequestEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private AdagraPlayer requester;
    private AdagraPlayer reciever;

    public DuelRequestEvent(AdagraPlayer requester, AdagraPlayer reciever) {
        this.requester = requester;
        this.reciever = reciever;
    }

    public AdagraPlayer getReciever() {
        return reciever;
    }

    public AdagraPlayer getRequester() {
        return requester;
    }
}
