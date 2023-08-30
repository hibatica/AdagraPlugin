package me.hibatica.adagraplugin;

import me.hibatica.adagraplugin.command.DuelCommand;
import me.hibatica.adagraplugin.command.DuelStatsCommand;
import me.hibatica.adagraplugin.command.DuelStopCommand;
import me.hibatica.adagraplugin.duels.duelmanager.DuelManager;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequestManager;
import me.hibatica.adagraplugin.duels.duelmanager.DuelManagerListeners;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequestManagerListeners;
import me.hibatica.adagraplugin.duels.event.DuelRequestAcceptedEvent;
import me.hibatica.adagraplugin.duels.inventorymanager.DuelInventoryManager;
import me.hibatica.adagraplugin.duels.inventorymanager.DuelInventoryManagerListeners;
import me.hibatica.adagraplugin.playermanager.PlayerManagerListeners;
import me.hibatica.adagraplugin.playerdata.PlayerDataStorage;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class AdagraPlugin extends JavaPlugin {

    private FileConfiguration config;

    private List<ItemStack[][]> duelKit = new ArrayList<>();

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.config = getConfig();

        PlayerManager.init(this);
        PlayerDataStorage.init(this);

        DuelRequestManager.init(this);
        DuelManager.init(this);
        DuelInventoryManager.init(this);

        loadDuelKit();

        getServer().getPluginManager().registerEvents(new PlayerManagerListeners(), this);

        getServer().getPluginManager().registerEvents(new DuelManagerListeners(this), this);
        getServer().getPluginManager().registerEvents(new DuelRequestManagerListeners(), this);
        getServer().getPluginManager().registerEvents(new DuelInventoryManagerListeners(this), this);

        getCommand("duelstats").setExecutor(new DuelStatsCommand(this));
        getCommand("duel").setExecutor(new DuelCommand(this));
        getCommand("duelstop").setExecutor(new DuelStopCommand(this));

    }

    @Override
    public void onDisable() {
        try {
            PlayerDataStorage.shutdown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PlayerManager.shutdown();

        DuelRequestManager.shutdown();
        DuelManager.shutdown();
        DuelInventoryManager.shutdown();
    }

    private void loadDuelKit() {

        ConfigurationSection kitSection = getConfig().getConfigurationSection("kit");

        if (kitSection == null) {
            getLogger().warning("No kit section found in the configuration.");
            return;
        }

        int kitSize = kitSection.getKeys(false).size();
        ItemStack[][] kitArray = new ItemStack[kitSize][2]; // Create array with appropriate size

        int index = 0;
        for (String itemName : kitSection.getKeys(false)) {
            int itemAmount = kitSection.getInt(itemName + ".amount"); // Get item amount

            Material material = Material.matchMaterial(itemName);
            if (material == null) {
                getLogger().warning("Invalid material name: " + itemName);
                continue;
            }

            ItemStack itemStack = new ItemStack(material, itemAmount);
            kitArray[index] = new ItemStack[]{itemStack};
            index++;
        }

        duelKit.add(kitArray);
    }

    public List<ItemStack[][]> getDuelKit() {
        return this.duelKit;
    }
}
