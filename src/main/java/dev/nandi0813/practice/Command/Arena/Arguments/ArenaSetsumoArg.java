package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaSetsumoArg
{

    public static void SetsumoCommand(Player player, String label, String[] args)
    {
        if (args.length == 3)
        {
            String arenaName = args[1];
            Arena arena = Practice.getArenaManager().getArena(arenaName);
            if (arena != null)
            {
                if (arena.isEnabled())
                {
                    player.sendMessage(StringUtil.CC("&cYou can't edit a enabled arena."));
                    return;
                }

                if (args[2].equalsIgnoreCase("true"))
                {
                    arena.changeSumoStatus();
                    player.sendMessage(StringUtil.CC("&6" + arena.getName() + " &ais now a &esumo &aarena."));
                    player.sendMessage(StringUtil.CC("&cIf the arena contained any specific ladder, it got removed, because you changed the sumo status."));
                }
                else if (args[2].equalsIgnoreCase("false"))
                {
                    arena.changeSumoStatus();
                    player.sendMessage(StringUtil.CC("&6" + arena.getName() + " &ais now a &enon-sumo &aarena."));
                    player.sendMessage(StringUtil.CC("&cIf the arena contained any specific ladder, it got removed, because you changed the sumo status."));
                }
                else
                {
                    player.sendMessage(StringUtil.CC("&cInvalid boolean!"));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setsumo <name> <true/false>"));
        }
    }

}
