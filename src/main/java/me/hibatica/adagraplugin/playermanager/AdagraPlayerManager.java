package me.hibatica.adagraplugin.playermanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.playerdata.PlayerDataStorage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AdagraPlayerManager {
    private static AdagraPlugin plugin;

    private static HashMap<String, AdagraPlayer> players;

    public static void init(AdagraPlugin plugin) {
        AdagraPlayerManager.plugin = plugin;
        players = new HashMap<>();
    }

    public static void shutdown() {
        for(String key : players.keySet()) {
            players.remove(key);
        }
    }

    public static AdagraPlayer getAdagraPlayer(String name) {
        return players.get(name);
    }

    protected static void playerJoined(Player player) {
        players.put(player.getName(), new AdagraPlayer(player));
    }

    protected static void playerLeft(Player player) {
        players.remove(player.getName());
    }

}
