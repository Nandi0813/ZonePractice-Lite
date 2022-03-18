package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaInfoArg
{

    public static void InfoCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            String arenaName = args[1];
            Arena arena = SystemManager.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
                player.sendMessage(StringUtil.CC(" &7» &3Arena: &b" + arena.getName()));
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Build: &b" + StringUtil.getStatus(arena.isBuild())));
                player.sendMessage(StringUtil.CC(" &7» &3Ladders: &b" + ArenaUtil.getLadderNames(arena)));
                player.sendMessage("");
                if (arena.getCorner1() != null)
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 1: &b" + arena.getCorner1().getWorld().getName() + ", " + arena.getCorner1().getX() + ", " + arena.getCorner1().getY() + ", " + arena.getCorner1().getZ()));
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &b» &3Corner 1: &cnull"));
                }
                if (arena.getCorner2() != null)
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 2: &b" + arena.getCorner2().getWorld().getName() + ", " + arena.getCorner2().getX() + ", " + arena.getCorner2().getY() + ", " + arena.getCorner2().getZ()));
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Corner 2: &cnull"));
                }
                player.sendMessage("");
                if (arena.getPosition1() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Player 1 Position: &b" + arena.getPosition1().getWorld().getName() + ", " + Math.ceil(arena.getPosition1().getX()) + ", " + Math.ceil(arena.getPosition1().getY()) + ", " + Math.ceil(arena.getPosition1().getZ()), "/arena teleport " + arenaName + " 1", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Player 1 Position: &cnull"));
                }
                if (arena.getPosition2() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Player 2 Position: &b" + arena.getPosition2().getWorld().getName() + ", " + Math.ceil(arena.getPosition2().getX()) + ", " + Math.ceil(arena.getPosition2().getY()) + ", " + Math.ceil(arena.getPosition2().getZ()), "/arena teleport " + arenaName + " 2", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Player 2 Position: &cnull"));
                }
                if (arena.getPosition3() != null)
                {
                    ClickableMessageUtil.sendClickableMessage(player, " &7» &3Spectator Position: &b" + arena.getPosition3().getWorld().getName() + ", " + Math.ceil(arena.getPosition3().getX()) + ", " + Math.ceil(arena.getPosition3().getY()) + ", " + Math.ceil(arena.getPosition3().getZ()), "/arena teleport " + arenaName + " 3", "&aTeleport to location.");
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Spectator Position: &cnull"));
                }
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Status: &b" + StringUtil.getStatus(arena.isEnabled())));
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
