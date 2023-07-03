package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class SetRegen
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setregen <ladder_id>/<ladder_name>"));
            return;
        }

        Ladder ladder;
        if (StringUtil.isInteger(args[1]))
            ladder = Practice.getLadderManager().getLadder(Integer.parseInt(args[1]));
        else
            ladder = Practice.getLadderManager().getLadder(args[1]);

        if (ladder == null)
        {
            player.sendMessage(StringUtil.CC("&cInvalid ladder id or name."));
            return;
        }

        if (ladder.isEnabled())
        {
            player.sendMessage(StringUtil.CC("&cYou can't edit an enabled ladder."));
            return;
        }

        if (ladder.isRegen())
        {
            ladder.setRegen(false);
            player.sendMessage(StringUtil.CC("&eYou &cdisabled &ethe ID" + ladder.getId() + " &eladder's regeneration setting."));
        }
        else
        {
            ladder.setRegen(true);
            player.sendMessage(StringUtil.CC("&eYou &eenabled &ethe ID" + ladder.getId() + " &eladder's regeneration setting."));
        }
    }

}
