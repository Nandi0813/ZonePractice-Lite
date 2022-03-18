package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class PartyHelpArg
{

    public static void HelpCommand(Player player, String label)
    {
        player.sendMessage(StringUtil.CC("&7&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &b&lParty Help &7- &fInformation on how to use party commands."));
        player.sendMessage(StringUtil.CC("&7&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &bParty Commands:"));
        player.sendMessage(StringUtil.CC(" &f/" + label + " create &8- &7Create a party."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " accept <player> &8- &7Accept a party invitation."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " leave &8- &7Accept a party invitation."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " info <player> &8- &7Party information."));
        player.sendMessage(StringUtil.CC(""));
        player.sendMessage(StringUtil.CC(" &bParty Leader Commands:"));
        player.sendMessage(StringUtil.CC(" &f/" + label + " invite <player> &8- &7Invite a player to your party."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " kick <player> &8- &7Kick a player from your party."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " leader <player> &8- &7Transfer party leadership."));
        player.sendMessage(StringUtil.CC(" &f/" + label + " disband &8- &7Disband party."));
        player.sendMessage(StringUtil.CC("&7&m------------------------------------------------"));
    }

}
