package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetNameArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 3)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setname <ladder_id> <name>"));
            return;
        }

        Ladder ladder = null;
        if (StringUtil.isInteger(args[1]))
            ladder = Practice.getLadderManager().getLadder(Integer.parseInt(args[1]));

        if (ladder == null)
        {
            player.sendMessage(StringUtil.CC("&cInvalid ladder id."));
            return;
        }

        if (ladder.isEnabled())
        {
            player.sendMessage(StringUtil.CC("&cYou can't edit an enabled ladder."));
            return;
        }

        String name = args[2];
        if (name.length() < 2 || name.length() > 13)
        {
            player.sendMessage(StringUtil.CC("&cName must be at least 2 and maximum 13 characters."));
            return;
        }

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        if (m.find())
        {
            player.sendMessage(StringUtil.CC("&cName shouldn't contain any special character."));
            return;
        }

        if (Practice.getLadderManager().getLadder(name) != null)
        {
            player.sendMessage(StringUtil.CC("&cLadder name is already in use."));
            return;
        }

        ladder.setName(name);
        player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's name to &a" + ladder.getName() + "&e."));
    }

}
