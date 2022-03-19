package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyLeaderArg
{

    public static void LeaderCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            Party party = SystemManager.getPartyManager().getParty(player);

            if (party != null)
            {
                if (party.getLeader().equals(player))
                {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null)
                    {
                        if (party.getMembers().contains(target))
                        {
                            party.setNewOwner(target);
                        }
                        else
                            player.sendMessage(LanguageManager.getString("party.player-not-member"));
                    }
                    else
                        player.sendMessage(LanguageManager.getString("party.player-not-online"));
                }
                else
                    player.sendMessage(LanguageManager.getString("party.not-leader"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.not-member"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " leader <player>"));
    }

}
