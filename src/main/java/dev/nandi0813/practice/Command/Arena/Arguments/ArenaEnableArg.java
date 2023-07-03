package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaEnableArg
{

    public static void EnableCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            String arenaName = args[1];
            Arena arena = Practice.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                if (!arena.isEnabled())
                {
                    ArenaUtil.changeStatus(player, arena);
                }
                else
                    player.sendMessage(StringUtil.CC("&cArena is already enabled."));
            }
            else
                player.sendMessage(StringUtil.CC("&cArena doesn't exists."));
        }
        else
            player.sendMessage(StringUtil.CC(" &c » /" + label + " enable <name>"));
    }

}
