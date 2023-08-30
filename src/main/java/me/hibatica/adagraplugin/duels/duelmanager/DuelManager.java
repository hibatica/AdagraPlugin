package me.hibatica.adagraplugin.duels.duelmanager;

import me.hibatica.adagraplugin.AdagraPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DuelManager {

    private static AdagraPlugin plugin;

    private static HashMap<String, Duel> duels;

    public static void init(AdagraPlugin plugin) {
        DuelManager.plugin = plugin;
        DuelManager.duels = new HashMap<>();
    }

    public static void shutdown() {

    }

    protected static void registerDuel(Duel duel) {
        duels.put(duel.getPlayer1().getBukkitPlayer().getName(), duel);
        duels.put(duel.getPlayer2().getBukkitPlayer().getName(), duel);
    }

    protected static void removeDuel(Duel duel) {
        duels.remove(duel.getPlayer2().getBukkitPlayer().getName());
        duels.remove(duel.getPlayer1().getBukkitPlayer().getName());
    }

    public static boolean playerIsInDuel(Player player) {
        Duel duel = duels.get(player.getName());

        if(duel != null) {
            return true;
        }

        return false;
    }

    public static Duel getDuel(Player player) {
        return duels.get(player.getName());
    }
}
