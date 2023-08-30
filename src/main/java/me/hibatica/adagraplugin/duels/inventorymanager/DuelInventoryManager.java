package me.hibatica.adagraplugin.duels.inventorymanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DuelInventoryManager {

    private static AdagraPlugin plugin;

    private static HashMap<UUID, ItemStack[]> inventoryContents;
    private static HashMap<UUID, ItemStack[]> inventoryArmorContents;

    public static void init(AdagraPlugin plugin) {
        DuelInventoryManager.plugin = plugin;
        inventoryContents = new HashMap<>();
        inventoryArmorContents = new HashMap<>();
    }

    public static void shutdown() {
        for (UUID playerId : inventoryContents.keySet()) {

            Player bukkitPlayer = plugin.getServer().getPlayer(playerId);
            if(bukkitPlayer == null) return;

            AdagraPlayer player = PlayerManager.getAdagraPlayer(bukkitPlayer.getName());
            if (player != null) {
                restoreInventory(player);
            }
        }
    }

    protected static void applyDuelInventory(AdagraPlayer player) {
        if(player == null) return;

        saveInventory(player);

        player.getBukkitPlayer().getInventory().clear();

        List<ItemStack[][]> duelKit = plugin.getDuelKit();

        for (ItemStack[][] kitLayer : duelKit) {
            for (ItemStack[] kitRow : kitLayer) {
                for (ItemStack item : kitRow) {
                    if (item != null) {
                        player.getBukkitPlayer().getInventory().addItem(item);
                    }
                }
            }
        }


    }

    protected static void restoreInventory(AdagraPlayer player) {
        if(player == null) return;

        player.getBukkitPlayer().getInventory().clear();

        player.getBukkitPlayer().getInventory().setArmorContents(inventoryArmorContents.get(player.getBukkitPlayer().getUniqueId()));
        player.getBukkitPlayer().getInventory().setContents(inventoryContents.get(player.getBukkitPlayer().getUniqueId()));

    }

    private static void saveInventory(AdagraPlayer player) {
        if(player == null) return;

        inventoryContents.put(player.getBukkitPlayer().getUniqueId(), player.getBukkitPlayer().getInventory().getContents());
        inventoryArmorContents.put(player.getBukkitPlayer().getUniqueId(), player.getBukkitPlayer().getInventory().getArmorContents());
    }

}
