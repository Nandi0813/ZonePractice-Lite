package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ListArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 1)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " list"));
            return;
        }

        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
        for (Ladder ladder : Practice.getLadderManager().getLadders())
        {
            if (ladder.getName() == null)
                player.sendMessage(StringUtil.CC(" &7» &6ID" + ladder.getId()));
            else
                player.sendMessage(StringUtil.CC(" &7» &6ID" + ladder.getId() + " &f- &a" + ladder.getName()));
        }
        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
    }

}
