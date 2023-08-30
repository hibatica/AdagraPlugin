package me.hibatica.adagraplugin.command;

import me.hibatica.adagraplugin.AdagraPlayer;
import me.hibatica.adagraplugin.AdagraPlugin;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequest;
import me.hibatica.adagraplugin.duels.duelrequestsmanager.DuelRequestManager;
import me.hibatica.adagraplugin.duels.event.DuelRequestAcceptedEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestDeniedEvent;
import me.hibatica.adagraplugin.duels.event.DuelRequestEvent;
import me.hibatica.adagraplugin.playermanager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DuelCommand implements CommandExecutor {

    private AdagraPlugin plugin;

    public DuelCommand(AdagraPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

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


        if(args.length == 0) {

            player.getBukkitPlayer().sendMessage(ChatColor.BOLD + "Duel Command Usage:");
            player.getBukkitPlayer().sendMessage("/duel PlayerName");
            player.getBukkitPlayer().sendMessage("/duel accept PlayerName");
            player.getBukkitPlayer().sendMessage("/duel deny PlayerName");
            player.getBukkitPlayer().sendMessage("/duelstats PlayerName");
            player.getBukkitPlayer().sendMessage("/duelstop");

            return true;
        }

        if(Objects.equals(args[0], "accept")) {

            if(args.length != 2) {

                player.getBukkitPlayer().sendMessage("Please specify a player to accept. /duel accept PlayerName");

                return true;
            }

            AdagraPlayer challenger = PlayerManager.getAdagraPlayer(args[1]);

            if(challenger == null) {

                player.getBukkitPlayer().sendMessage(args[1] + " is not online");

                return true;
            }

            DuelRequest request = DuelRequestManager.getRequest(challenger.getBukkitPlayer().getName());

            if(request == null) {
                player.getBukkitPlayer().sendMessage(args[1] + " has not challenged you");

                return true;
            }

            plugin.getServer().getPluginManager().callEvent(new DuelRequestAcceptedEvent(challenger, player));

            return true;
        }

        if(Objects.equals(args[0], "deny")) {


            if(args.length != 2) {

                player.getBukkitPlayer().sendMessage("Please specify a player to deny. /duel deny PlayerName");

                return true;
            }

            AdagraPlayer challenger = PlayerManager.getAdagraPlayer(args[1]);

            if(challenger == null) {

                player.getBukkitPlayer().sendMessage(args[1] + " is not online");

                return true;
            }

            DuelRequest request = DuelRequestManager.getRequest(challenger.getBukkitPlayer().getName());

            if(request == null) {
                player.getBukkitPlayer().sendMessage(args[1] + " has not challenged you");

                return true;
            }

            plugin.getServer().getPluginManager().callEvent(new DuelRequestDeniedEvent(challenger, player));


            return true;
        }

        // if args is not handled yet, it must be player

        AdagraPlayer challengedPlayer = PlayerManager.getAdagraPlayer(args[0]);

        if(challengedPlayer == null) {

            player.getBukkitPlayer().sendMessage(args[0] + " is not online");

            return true;
        }

        if(challengedPlayer.getBukkitPlayer().getName().equals(player.getBukkitPlayer().getName())) {
            player.getBukkitPlayer().sendMessage("You cannot challenge yourself");

            return true;
        }

        plugin.getServer().getPluginManager().callEvent(new DuelRequestEvent(player, challengedPlayer));

        return true;
    }
}
