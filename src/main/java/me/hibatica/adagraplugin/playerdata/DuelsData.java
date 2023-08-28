package me.hibatica.adagraplugin.playerdata;

import java.util.UUID;

public class DuelsData {
    private int wins;
    private int losses;
    private UUID uuid;

    public DuelsData(UUID uuid, int wins, int losses) {
        this.uuid = uuid;
        this.losses = losses;
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        wins = PlayerDataStorage.incrementPlayerDuelWins(uuid);
    }

    public void incrementLosses() {
        losses = PlayerDataStorage.incrementPlayerDuelLosses(uuid);
    }
}
