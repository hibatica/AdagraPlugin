package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelRequestDeniedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private AdagraPlayer denier;

    private AdagraPlayer deniedPlayer;

    public DuelRequestDeniedEvent(AdagraPlayer deniedPlayer, AdagraPlayer denier) {
        this.deniedPlayer = deniedPlayer;
        this.denier = denier;
    }

    public AdagraPlayer getDeniedPlayer() {
        return deniedPlayer;
    }

    public AdagraPlayer getDenier() {
        return denier;
    }
}
