package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class PartyLeaveArg
{

    public static void LeaveCommand(Player player, String label, String[] args)
    {
        if (args.length == 1)
        {
            Party party = SystemManager.getPartyManager().getParty(player);

            if (party != null)
            {
                if (party.getMatch() == null)
                {
                    party.removeMember(player, false);
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou can't leave the party during match."));
            }
            else
                player.sendMessage(StringUtil.CC("&cYou are not a member of a party."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " leave"));
    }

}
