package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class SetHitdelay
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 3)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " hitdelay <ladder_id>/<ladder_name> <hit_delay>"));
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

        int hitdelay = Integer.parseInt(args[2]);
        if (hitdelay < 0 || hitdelay > 100)
        {
            player.sendMessage(StringUtil.CC("&cHit delay has to be at least 0 and maximum 100."));
            return;
        }

        ladder.setHitDelay(hitdelay);
        player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's hit delay to &c" + hitdelay + "&e."));
    }

}
