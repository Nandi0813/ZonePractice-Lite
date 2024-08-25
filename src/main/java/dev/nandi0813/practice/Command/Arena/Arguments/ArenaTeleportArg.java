package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaTeleportArg
{

    public static void TeleportCommand(Player player, String label, String[] args)
    {
        if (args.length == 3)
        {
            String arenaName = args[1];
            int position = Integer.parseInt(args[2]);
            Arena arena = Practice.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                if (position == 1)
                {
                    player.teleport(arena.getPosition1());
                }
                else if (position == 2)
                {
                    player.teleport(arena.getPosition2());
                }
                else if (position == 3) // Spectator
                {
                    player.teleport(arena.getPosition3());
                }
                else
                {
                    player.sendMessage(StringUtil.CC("&cInvalid number!"));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " teleport <name> <1/2/3>"));
        }
    }

}