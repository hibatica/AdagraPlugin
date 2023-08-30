package me.hibatica.adagraplugin.duels.duelrequestsmanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.duels.event.DuelRequestAcceptedEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestDeniedEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestTimeoutEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DuelRequestManagerListeners implements Listener {
    @EventHandler
    public void onDuelRequest(DuelRequestEvent event) {

        DuelRequestManager.registerNewRequest(event.getRequester(), event.getReciever());
    }

    @EventHandler
    public void onDuelRequestAccepted(DuelRequestAcceptedEvent event) {
        DuelRequest request = DuelRequestManager.getRequest(event.getAcceptedPlayer().getBukkitPlayer().getName());

        if(request == null) {
            return;
        }

        request.notifyPlayersOfAccepted();

        // initiation of duel is handled by DuelManagerListeners and DuelManager
        DuelRequestManager.removeRequest(event.getAcceptedPlayer());
    }

    @EventHandler
    public void onDuelRequestDenied(DuelRequestDeniedEvent event) {
        DuelRequest request = DuelRequestManager.getRequest(event.getDeniedPlayer().getBukkitPlayer().getName());

        if(request == null) {
            return;
        }

        request.notifyPlayersOfDenied();

        DuelRequestManager.removeRequest(event.getDeniedPlayer());
    }

    @EventHandler
    public void onDuelRequestTimeout(DuelRequestTimeoutEvent event) {
        AdagraPlayer player = event.getRequest().getRequester();

        player.getBukkitPlayer().sendMessage("Your request to duel " + event.getRequest().getReciever().getBukkitPlayer().getName() + " has timed out");
    }
}
