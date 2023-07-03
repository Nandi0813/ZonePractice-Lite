package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaSetbuildArg
{

    public static void SetbuildCommand(Player player, String label, String[] args)
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
                    arena.changeBuildStatus();
                    player.sendMessage(StringUtil.CC("&6" + arena.getName() + " &ais now a &ebuild &aarena."));
                    player.sendMessage(StringUtil.CC("&cIf the arena contained any specific ladder, it got removed, because you changed the build status."));
                }
                else if (args[2].equalsIgnoreCase("false"))
                {
                    arena.changeBuildStatus();
                    player.sendMessage(StringUtil.CC("&6" + arena.getName() + " &ais now a &enon-build &aarena."));
                    player.sendMessage(StringUtil.CC("&cIf the arena contained any specific ladder, it got removed, because you changed the build status."));
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
            player.sendMessage(StringUtil.CC("&c/" + label + " setbuild <name> <true/false>"));
        }
    }

}
