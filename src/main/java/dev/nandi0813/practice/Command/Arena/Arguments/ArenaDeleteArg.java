package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaDeleteArg
{

    public static void DeleteCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            String arenaName = args[1];
            Arena arena = Practice.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                if (!arena.isEnabled())
                {
                    Practice.getArenaManager().getArenas().remove(arena);
                    arena.deleteData();
                    player.sendMessage(StringUtil.CC("&aYou successfully &cdeleted &aarena &6" + arenaName + "&a."));
                }
                else
                {
                    player.sendMessage(StringUtil.CC("&cYou can't delete an enabled arena."));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " delete <name>"));
        }
    }

}
