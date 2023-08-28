package me.hibatica.adagraplugin;

import me.hibatica.adagraplugin.playermanager.AdagraPlayerManagerListeners;
import me.hibatica.adagraplugin.playerdata.PlayerDataStorage;
import me.hibatica.adagraplugin.playermanager.AdagraPlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class AdagraPlugin extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.config = getConfig();

        AdagraPlayerManager.init(this);
        PlayerDataStorage.init(this);

        getServer().getPluginManager().registerEvents(new AdagraPlayerManagerListeners(), this);

    }

    @Override
    public void onDisable() {
        try {
            PlayerDataStorage.shutdown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        AdagraPlayerManager.shutdown();
    }
}
