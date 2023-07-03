package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyInfoArg
{

    public static void InfoCommand(Player player, String label, String[] args)
    {
        if (args.length == 1 || args.length == 2)
        {
            Party party;

            if (args.length == 1)
            {
                party = Practice.getPartyManager().getParty(player);
                if (party == null)
                {
                    player.sendMessage(LanguageManager.getString("party.not-member"));
                    return;
                }
            }
            else
            {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null)
                {
                    player.sendMessage(LanguageManager.getString("party.player-not-online"));
                    return;
                }

                party = Practice.getPartyManager().getParty(target);
                if (party == null)
                {
                    player.sendMessage(LanguageManager.getString("party.not-member"));
                    return;
                }
            }

            for (String line : LanguageManager.getList("party.info"))
            {
                player.sendMessage(line
                        .replaceAll("%leader%", party.getLeader().getName())
                        .replaceAll("%maxSize%", String.valueOf(party.getMaxPlayerLimit()))
                        .replaceAll("%size%", String.valueOf(party.getMembers().size()))
                        .replaceAll("%members%", party.getMemberNames().toString().replace("[", "").replace("]", ""))
                );
            }
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " info / info <player>"));
    }

}
