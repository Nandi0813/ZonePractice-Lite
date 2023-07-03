package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.KnockbackType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class SetCombo
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setcombo <ladder_id>/<ladder_name>"));
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

        if (ladder.getKnockbackType().equals(KnockbackType.DEFAULT))
        {
            ladder.setKnockbackType(KnockbackType.COMBO);
            player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's knockback to &cCombo&e."));
        }
        else
        {
            ladder.setKnockbackType(KnockbackType.DEFAULT);
            player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's knockback to &cDefault&e."));
        }
    }

}
