package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyInviteArg
{

    public static void InviteCommand(Player player, String label, String[] args)
    {
        Party party = Practice.getPartyManager().getParty(player);

        if (args.length == 2)
        {
            if (party != null)
            {
                if (party.getLeader().equals(player))
                {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null)
                    {
                        if (player != target)
                        {
                            if (!party.getMembers().contains(target))
                            {
                                party.getInvites().put(target, System.currentTimeMillis());

                                party.sendMessage(LanguageManager.getString("party.player-invited-party").replaceAll("%player%", target.getName()));
                                ClickableMessageUtil.sendClickableMessage(target, LanguageManager.getString("party.player-invited-player").replaceAll("%player%", player.getName()), "/party accept " + party.getLeader().getName(), "&aClick to accept");
                            }
                            else
                                player.sendMessage(LanguageManager.getString("party.player-already-member").replaceAll("%player%", target.getName()));
                        }
                        else
                            player.sendMessage(LanguageManager.getString("party.cant-invite-yourself"));
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
            player.sendMessage(StringUtil.CC("&c/" + label + " invite <player>"));
    }

}
