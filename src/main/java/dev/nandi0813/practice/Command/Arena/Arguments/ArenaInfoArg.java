package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaInfoArg
{

    public static void InfoCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            String arenaName = args[1];
            Arena arena = Practice.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                List<String> ladderNames = ArenaUtil.getLadderNames(arena);

                player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
                player.sendMessage(StringUtil.CC(" &7» &3Arena: &b" + arena.getName()));
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Build: &b" + StringUtil.getStatus(arena.isBuild()) + "d"));

                if (ladderNames.isEmpty())
                    player.sendMessage(StringUtil.CC(" &7» &3Ladders: &cNULL"));
                else
                    player.sendMessage(StringUtil.CC(" &7» &3Ladders: &b" + ArenaUtil.getLadderNames(arena).toString().replace("[", "").replace("]", "")));

                player.sendMessage("");
                if (arena.getCorner1() != null)
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 1: &b" + Math.round(arena.getCorner1().getX()) + ", " + Math.round(arena.getCorner1().getY()) + ", " + Math.round(arena.getCorner1().getZ())));
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &b» &3Corner 1: &cnull"));
                }
                if (arena.getCorner2() != null)
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 2: &b" + Math.round(arena.getCorner2().getX()) + ", " + Math.round(arena.getCorner2().getY()) + ", " + Math.round(arena.getCorner2().getZ())));
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 2: &cnull"));
                }
                player.sendMessage("");
                if (arena.getPosition1() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Player 1 Position: &b" + Math.round(arena.getPosition1().getX()) + ", " + Math.round(arena.getPosition1().getY()) + ", " + Math.round(arena.getPosition1().getZ()), "/arena teleport " + arenaName + " 1", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Player 1 Position: &cnull"));
                }
                if (arena.getPosition2() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Player 2 Position: &b" + Math.round(arena.getPosition2().getX()) + ", " + Math.round(arena.getPosition2().getY()) + ", " + Math.round(arena.getPosition2().getZ()), "/arena teleport " + arenaName + " 2", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Player 2 Position: &cnull"));
                }
                if (arena.getPosition3() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Spectator Position: &b" + Math.round(arena.getPosition3().getX()) + ", " + Math.round(arena.getPosition3().getY()) + ", " + Math.round(arena.getPosition3().getZ()), "/arena teleport " + arenaName + " 3", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Spectator Position: &cnull"));
                }
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Status: &b" + StringUtil.getStatus(arena.isEnabled()) + "d"));
                player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " info <name>"));
        }
    }

}
