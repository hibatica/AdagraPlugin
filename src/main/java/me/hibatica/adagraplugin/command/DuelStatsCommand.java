package me.hibatica.adagraplugin.command;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.playerdata.DuelsData;
import me.hibatica.adagraplugin.playerdata.PlayerData;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DuelStatsCommand implements CommandExecutor {

    private AdagraPlugin plugin;

    public DuelStatsCommand(AdagraPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        AdagraPlayer player = PlayerManager.getAdagraPlayer(commandSender.getName());

        if(commandSender instanceof ConsoleCommandSender) {
            plugin.getLogger().info("The console cannot run the command /duelstats");

            return true;
        }

        if(player == null) {
            return true;
        }

        if(!player.getBukkitPlayer().hasPermission("adagraplugin.duel")) {
            player.getBukkitPlayer().sendMessage("You do not have permission to use this command.");

            return true;
        }

        AdagraPlayer selectedPlayer = null;
        String selectedPlayerName = null;

        if(args.length == 0) {
            selectedPlayer = player;
            selectedPlayerName = player.getBukkitPlayer().getName();
        } else {
            selectedPlayer = PlayerManager.getAdagraPlayer(args[0]);
            if(selectedPlayer != null) {
                selectedPlayerName = selectedPlayer.getBukkitPlayer().getName();
            }
        }

        if(selectedPlayerName == null) {
            player.getBukkitPlayer().sendMessage("'" + selectedPlayer + "' is not online");

            return true;
        }

        DuelsData duelsData = selectedPlayer.getPlayerData().getDuelsData();

        int kDRatio = 0;

        if(duelsData.getLosses() != 0) {
            kDRatio = duelsData.getWins() / duelsData.getLosses();
        }

        player.getBukkitPlayer().sendMessage(ChatColor.BOLD + selectedPlayerName + "'s Dueling Stats");
        player.getBukkitPlayer().sendMessage("Wins: " + duelsData.getWins());
        player.getBukkitPlayer().sendMessage("Losses: " + duelsData.getLosses());
        player.getBukkitPlayer().sendMessage("K/D Ratio: " + kDRatio);


        return true;
    }
}
