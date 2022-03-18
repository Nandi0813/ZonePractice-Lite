package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.SystemManager;
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
                party = SystemManager.getPartyManager().getParty(player);
                if (party == null)
                {
                    player.sendMessage(StringUtil.CC("&cYou are not in a party."));
                    return;
                }
            }
            else
            {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null)
                {
                    player.sendMessage(StringUtil.CC("&c" + args[1] + " is not online."));
                    return;
                }

                party = SystemManager.getPartyManager().getParty(target);
                if (party == null)
                {
                    player.sendMessage(StringUtil.CC("&c" + target.getName() + " is not in a party."));
                    return;
                }
            }

            player.sendMessage(StringUtil.CC("&7&m------------------------------------------------"));
            player.sendMessage(StringUtil.CC(" &b&lParty Info&7:"));
            player.sendMessage(StringUtil.CC(""));
            player.sendMessage(StringUtil.CC(" &bLeader&7: &f" + party.getLeader().getName()));
            player.sendMessage(StringUtil.CC(" &bParty Members (" + party.getMaxPlayerLimit() + "/" + party.getMembers().size() + ")&7: &f" + party.getMemberNames().toString().replace("[", "").replace("]", "")));
            player.sendMessage(StringUtil.CC("&7&m------------------------------------------------"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " info / info <player>"));
    }

}
