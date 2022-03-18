package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Util.CornerUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaSetcornerArg
{

    public static void SetcornerCommand(Player player, String label, String[] args)
    {
        if (args.length == 3)
        {
            CornerUtil.setArenaCorners(args[1], player, Integer.parseInt(args[2]));
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setcorner <name> <1/2>"));
        }
    }

}
