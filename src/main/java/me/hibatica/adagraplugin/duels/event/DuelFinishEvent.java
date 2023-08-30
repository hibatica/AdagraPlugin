package me.hibatica.adagraplugin.duels.event;

import me.hibatica.adagraplugin.duels.duelmanager.Duel;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelFinishEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private Duel duel;

    public DuelFinishEvent(Duel duel) {
        this.duel = duel;
    }

    public Duel getDuel() {
        return duel;
    }
}
