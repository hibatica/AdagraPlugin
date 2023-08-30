package me.hibatica.adagraplugin.duels.duelrequestsmanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.duels.duelmanager.Duel;
import me.hibatica.adagraplugin.duels.event.DuelRequestTimeoutEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuelRequestManager {
    private static AdagraPlugin plugin;

    private static HashMap<String, DuelRequest> requests;

    public static void init(AdagraPlugin plugin) {
        DuelRequestManager.plugin = plugin;
        DuelRequestManager.requests = new HashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                purgeExpiredRequests();
            }
        }.runTaskTimer(plugin, 20L * 60, 20L * 60);
    }

    public static void shutdown() {

    }

    private static void purgeExpiredRequests() {
        Instant now = Instant.now();
        List<String> toRemove = new ArrayList<>();

        for (String requesterName : requests.keySet()) {
            DuelRequest request = requests.get(requesterName);
            if (request.getTimeoutTime().isBefore(now)) {
                plugin.getServer().getPluginManager().callEvent(new DuelRequestTimeoutEvent(request));
                toRemove.add(requesterName);
            }
        }

        for (String requesterName : toRemove) {
            requests.remove(requesterName);
        }
    }

    protected static void registerNewRequest(AdagraPlayer requester, AdagraPlayer reciever) {

        if(requests.get(requester.getBukkitPlayer().getName()) != null) {
            requests.remove(requester.getBukkitPlayer().getName());
        }

        DuelRequest request = new DuelRequest(requester, reciever);
        requests.put(requester.getBukkitPlayer().getName(), request);

        request.notifyPlayers();
    }

    protected  static void removeRequest(AdagraPlayer requester) {
        requests.remove(requester.getBukkitPlayer().getName());
    }

    public static DuelRequest getRequest(String requesterName) {
        return requests.get(requesterName);
    }
}
