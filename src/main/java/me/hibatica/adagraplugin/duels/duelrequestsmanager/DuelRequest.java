package me.hibatica.adagraplugin.duels.duelrequestsmanager;

import me.hibatica.adagraplugin.AdagraPlayer;

import java.time.Duration;
import java.time.Instant;

public class DuelRequest {
    private AdagraPlayer requester;
    private AdagraPlayer reciever;

    private Instant timeoutTime;

    public DuelRequest(AdagraPlayer requester, AdagraPlayer reciever) {
        this.reciever = reciever;
        this.requester = requester;
        this.timeoutTime = Instant.now().plus(Duration.ofMinutes(1));
    }

    protected void notifyPlayers() {
        requester.getBukkitPlayer().sendMessage("You have requested to duel " + reciever.getBukkitPlayer().getName());
        requester.getBukkitPlayer().sendMessage("Your request will time out in 1 minute");
        reciever.getBukkitPlayer().sendMessage("You have been challenged to a duel by " + requester.getBukkitPlayer().getName());
        reciever.getBukkitPlayer().sendMessage("Type /duel accept " + requester.getBukkitPlayer().getName() + " OR /duel deny " + requester.getBukkitPlayer().getName());
        reciever.getBukkitPlayer().sendMessage("This request will time out in 1 minute");
    }

    protected void notifyPlayersOfDenied() {
        requester.getBukkitPlayer().sendMessage(reciever.getBukkitPlayer().getName() + " has denied your duel request");
        reciever.getBukkitPlayer().sendMessage("You have denied " + requester.getBukkitPlayer().getName() + "'s duel request");
    }

    protected void notifyPlayersOfAccepted() {
        requester.getBukkitPlayer().sendMessage(reciever.getBukkitPlayer().getName() + " has accepted your duel!");
        reciever.getBukkitPlayer().sendMessage("You accepted " + requester.getBukkitPlayer().getName() + "'s duel!");
    }

    public AdagraPlayer getReciever() {
        return reciever;
    }

    public AdagraPlayer getRequester() {
        return requester;
    }

    public Instant getTimeoutTime() {
        return timeoutTime;
    }
}
