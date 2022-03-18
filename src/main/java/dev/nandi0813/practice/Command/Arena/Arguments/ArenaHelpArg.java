package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaHelpArg
{

    public static void HelpCommand(Player player, String label)
    {
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " list"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " info <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " create <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " delete <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " enable <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " disable <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " setcorner <name> <1/2>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " setposition <name> <1/2/3>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " setbuild <name> <true/false>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder list <name>"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder <add/remove> <name> <ladder>"));
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
    }

}
