package me.hibatica.adagraplugin.duels.inventorymanager;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.duels.duelmanager.Duel;
import me.hibatica.adagraplugin.duels.duelmanager.DuelManager;
import me.hibatica.adagraplugin.duels.event.DuelFinishEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestAcceptedEvent;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DuelInventoryManagerListeners implements Listener {

    private AdagraPlugin plugin;

    public DuelInventoryManagerListeners(AdagraPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDuelRequestAccepted(DuelRequestAcceptedEvent event) {

        DuelInventoryManager.applyDuelInventory(event.getAcceptedPlayer());
        DuelInventoryManager.applyDuelInventory(event.getAcceptor());
    }

    @EventHandler
    public void onDuelFinish(DuelFinishEvent event) {

        DuelInventoryManager.restoreInventory(event.getDuel().getPlayer1());
        DuelInventoryManager.restoreInventory(event.getDuel().getPlayer2());
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        if (DuelManager.playerIsInDuel(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(!DuelManager.playerIsInDuel(plugin.getServer().getPlayer(event.getWhoClicked().getName()))) return;

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().equals(event.getWhoClicked().getOpenInventory().getTopInventory())) {
            // Allow moving items within the inventory
            if (event.isShiftClick()) {
                // Prevent shift-clicking items out of the inventory
                if (event.getRawSlot() < event.getWhoClicked().getInventory().getSize()) {
                    event.setCancelled(true);
                }
            } else {
                // Prevent clicking items out of the inventory
                if (event.getRawSlot() < event.getWhoClicked().getInventory().getSize() + 9) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if(!DuelManager.playerIsInDuel(plugin.getServer().getPlayer(event.getWhoClicked().getName()))) return;

        if (event.getWhoClicked().getOpenInventory() == null ||
                !event.getWhoClicked().getOpenInventory().getTopInventory().equals(event.getInventory())) {
            return;
        }

        for (int slot : event.getRawSlots()) {
            if (slot < event.getWhoClicked().getInventory().getSize()) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
