package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class HelpArg
{

    public static void HelpCommand(Player player, String label)
    {
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " lobby - Teleport to lobby."));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " lobby set - Set lobby location."));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " arenas - Teleport to arenas world."));
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
    }

}
