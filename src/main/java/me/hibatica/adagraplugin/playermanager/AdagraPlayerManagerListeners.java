package me.hibatica.adagraplugin.playermanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AdagraPlayerManagerListeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AdagraPlayerManager.playerJoined(event.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AdagraPlayerManager.playerLeft(event.getPlayer());
    }
}
