package me.hibatica.adagraplugin.duels.duelmanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.duels.event.DuelFinishEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestAcceptedEvent;
import me.hibatica.adagraplugin.duels.event.DuelStopEvent;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelManagerListeners implements Listener {

    private AdagraPlugin plugin;

    public DuelManagerListeners(AdagraPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDuelFinish(DuelFinishEvent event) {

        event.getDuel().getPlayer1().getBukkitPlayer().sendMessage("Your duel has ended");
        event.getDuel().getPlayer2().getBukkitPlayer().sendMessage("Your duel has ended");

        DuelManager.removeDuel(event.getDuel());
    }

    @EventHandler
    public void onDuelRequestAccepted(DuelRequestAcceptedEvent event) {
        AdagraPlayer requester = event.getAcceptedPlayer();
        AdagraPlayer reciever = event.getAcceptor();

        Duel duel = new Duel(requester, reciever);

        DuelManager.registerDuel(duel);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();

        if(player != null && DuelManager.playerIsInDuel(player)) {
            Duel duel = DuelManager.getDuel(player);

            duel.playerLost(PlayerManager.getAdagraPlayer(player.getName()));

            plugin.getServer().getPluginManager().callEvent(new DuelFinishEvent(duel));

            DuelManager.removeDuel(duel);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(DuelManager.playerIsInDuel(player)) {
            Duel duel = DuelManager.getDuel(player);

            duel.noWinner();

            plugin.getServer().getPluginManager().callEvent(new DuelFinishEvent(duel));

            DuelManager.removeDuel(duel);
        }
    }

    @EventHandler
    public void onDuelStop(DuelStopEvent event) {
        if(DuelManager.playerIsInDuel(event.getPlayer())) {
            Duel duel = DuelManager.getDuel(event.getPlayer());
            duel.playerLost(PlayerManager.getAdagraPlayer(event.getPlayer().getName()));
            plugin.getServer().getPluginManager().callEvent(new DuelFinishEvent(duel));
            DuelManager.removeDuel(duel);
        }
    }
}
