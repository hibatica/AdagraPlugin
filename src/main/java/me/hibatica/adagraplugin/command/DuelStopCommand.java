package me.hibatica.adagraplugin.command;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.duels.duelmanager.Duel;
import me.hibatica.adagraplugin.duels.duelmanager.DuelManager;
import me.hibatica.adagraplugin.duels.event.DuelFinishEvent;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DuelStopCommand implements CommandExecutor {

    private AdagraPlugin plugin;

    public DuelStopCommand(AdagraPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        AdagraPlayer player = PlayerManager.getAdagraPlayer(commandSender.getName());

        if(player == null) {
            return true;
        }

        if(commandSender instanceof ConsoleCommandSender) {
            plugin.getLogger().info("The console cannot run the command /duelstats");

            return true;
        }

        if(!player.getBukkitPlayer().hasPermission("adagraplugin.duel")) {
            player.getBukkitPlayer().sendMessage("You do not have permission to use this command");

            return true;
        }

        if(!DuelManager.playerIsInDuel(player.getBukkitPlayer())) {
            player.getBukkitPlayer().sendMessage("You are not currently in a duel");

            return true;
        }

        Duel duel = DuelManager.getDuel(player.getBukkitPlayer());

        if(duel == null) {
            player.getBukkitPlayer().sendMessage("You are not currently in a duel");

            return true;
        }

        duel.playerLost(player);

        plugin.getServer().getPluginManager().callEvent(new DuelFinishEvent(duel));

        return false;
    }
}
