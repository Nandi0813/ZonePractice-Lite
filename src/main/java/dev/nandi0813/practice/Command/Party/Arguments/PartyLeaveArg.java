package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
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
                    player.sendMessage(LanguageManager.getString("party.cant-leave-inmatch"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.not-member"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " leave"));
    }

}
