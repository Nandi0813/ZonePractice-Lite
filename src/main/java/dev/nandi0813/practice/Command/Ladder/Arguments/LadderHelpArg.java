package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class LadderHelpArg
{

    public static void HelpCommand(Player player, String label)
    {
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " list"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " info <name>"));
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
    }

}
