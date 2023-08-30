package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelRequestAcceptedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private AdagraPlayer acceptor;
    private AdagraPlayer acceptedPlayer;

    public DuelRequestAcceptedEvent(AdagraPlayer acceptedPlayer, AdagraPlayer acceptor) {
        this.acceptedPlayer = acceptedPlayer;
        this.acceptor = acceptor;
    }

    public AdagraPlayer getAcceptedPlayer() {
        return acceptedPlayer;
    }

    public AdagraPlayer getAcceptor() {
        return acceptor;
    }
}
