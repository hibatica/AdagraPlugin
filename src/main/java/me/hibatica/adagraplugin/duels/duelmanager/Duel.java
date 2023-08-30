package me.hibatica.adagraplugin.duels.duelmanager;

import me.hibatica.adagraplugin.AdagraPlayer;

public class Duel {
    private AdagraPlayer player1;
    private AdagraPlayer player2;

    private AdagraPlayer winner;
    private AdagraPlayer loser;

    protected Duel(AdagraPlayer player1, AdagraPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.winner = null;
        this.loser = null;
    }

    public void noWinner() {
        this.winner = null;
        this.loser = null;

        finishDuel();

    }

    public void playerLost(AdagraPlayer player) {
        if(player.getBukkitPlayer().getName().equals(player1.getBukkitPlayer().getName())) {
            winner = player2;
            loser = player1;
        } else if(player.getBukkitPlayer().getName().equals(player2.getBukkitPlayer().getName())) {
            winner = player1;
            loser = player2;
        } else {
            winner = null;
            loser = null;
        }

        finishDuel();
    }

    public void playerWon(AdagraPlayer player) {
        if(player.getBukkitPlayer().getName().equals(player1.getBukkitPlayer().getName())) {
            winner = player1;
            loser = player2;
        } else if(player.getBukkitPlayer().getName().equals(player2.getBukkitPlayer().getName())) {
            winner = player2;
            loser = player1;
        } else {
            winner = null;
            loser = null;
        }

        finishDuel();
    }

    public AdagraPlayer getPlayer1() {
        return player1;
    }

    public AdagraPlayer getPlayer2() {
        return player2;
    }

    private void finishDuel() {

        if(winner != null) {
            winner.getPlayerData().getDuelsData().incrementWins();
        }

        if(loser != null) {
            loser.getPlayerData().getDuelsData().incrementLosses();
        }
    }
}
