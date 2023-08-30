package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelRequestTimeoutEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private DuelRequest request;

    public DuelRequestTimeoutEvent(DuelRequest request) {
        this.request = request;
    }

    public DuelRequest getRequest() {
        return request;
    }
}
