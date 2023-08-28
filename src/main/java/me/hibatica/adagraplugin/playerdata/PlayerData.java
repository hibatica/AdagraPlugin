package me.hibatica.adagraplugin.playerdata;

import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private DuelsData duelsData;

    public PlayerData(UUID playerUuid, DuelsData duelsData) {
        this.uuid = uuid;
        this.duelsData = duelsData;
    }

    public DuelsData getDuelsData() {
        return this.duelsData;
    }
}
