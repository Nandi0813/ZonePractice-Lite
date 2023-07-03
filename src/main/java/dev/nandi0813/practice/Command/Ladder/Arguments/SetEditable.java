package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class SetEditable
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " seteditable <ladder_id>/<ladder_name>"));
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

        if (ladder.isEditable())
        {
            ladder.setEditable(false);
            player.sendMessage(StringUtil.CC("&eYou &cdisabled &ethe ID" + ladder.getId() + " &eladder's editable setting."));
        }
        else
        {
            ladder.setEditable(true);
            player.sendMessage(StringUtil.CC("&eYou &eenabled &ethe ID" + ladder.getId() + " &eladder's editable setting."));
        }
    }

}
