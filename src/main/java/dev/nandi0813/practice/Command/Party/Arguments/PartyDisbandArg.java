package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class PartyDisbandArg
{

    public static void DisbandCommand(Player player, String label, String[] args)
    {
        if (args.length == 1)
        {
            Party party = Practice.getPartyManager().getParty(player);

            if (party != null)
            {
                if (party.getLeader().equals(player))
                {
                    party.disband();
                }
                else
                    player.sendMessage(LanguageManager.getString("party.not-leader"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.not-member"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " disband"));
    }

}
