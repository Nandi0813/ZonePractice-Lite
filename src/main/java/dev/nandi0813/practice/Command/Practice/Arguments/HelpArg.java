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
        player.sendMessage(StringUtil.CC(" &c » /" + label + " eloreset <player> <ladder/all> - Resets the players elo."));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " reset <player> - Resets the players stats."));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " resetall - &4Resets all player stats!"));
        player.sendMessage(StringUtil.CC(" &c » /" + label + " endmatch <player> - &cForce end player's match."));
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
    }

}
