package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyKickArg
{

    public static void KickCommand(Player player, String label, String[] args)
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
                        if (!target.equals(player))
                        {
                            if (party.getMembers().contains(target))
                            {
                                party.removeMember(target, true);
                            }
                            else
                                player.sendMessage(StringUtil.CC("&cPlayer is not a member of the party."));
                        }
                        else
                            player.sendMessage(StringUtil.CC("&cYou can't kick yourself out."));
                    }
                    else
                        player.sendMessage(StringUtil.CC("&cPlayer is not online."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou are not the leader of the party."));
            }
            else
                player.sendMessage(StringUtil.CC("&cYou are not a member of a party."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " kick <player>"));
    }

}
