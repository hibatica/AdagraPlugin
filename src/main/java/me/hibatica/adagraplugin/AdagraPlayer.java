package me.hibatica.adagraplugin;

import me.hibatica.adagraplugin.playerdata.PlayerData;
import me.hibatica.adagraplugin.playerdata.PlayerDataStorage;
import org.bukkit.entity.Player;

public class AdagraPlayer {
    private PlayerData playerData;
    private Player bukkitPlayer;

    public AdagraPlayer(Player player) {
        this.bukkitPlayer = player;
        this.playerData = PlayerDataStorage.getPlayerData(player.getUniqueId());
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
