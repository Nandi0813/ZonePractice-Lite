package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Command.Party.PartyCommand;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyInviteArg
{

    public static void InviteCommand(Player player, String label, String[] args)
    {
        Party party = SystemManager.getPartyManager().getParty(player);

        if (args.length == 2)
        {
            if (party != null)
            {
                if (party.getLeader().equals(player))
                {
                    Player target = Bukkit.getPlayer(args[1]);
                    Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);

                    if (target != null)
                    {
                        if (player != target)
                        {
                            if (!party.getMembers().contains(target))
                            {
                                if (targetProfile.isPartyInvites())
                                {
                                    party.getInvites().put(target, System.currentTimeMillis());

                                    party.sendMessage(StringUtil.CC(PartyCommand.getPrefix() + "&a" + target.getName() + " &7has been invited to the party!"));
                                    ClickableMessageUtil.sendClickableMessage(target, PartyCommand.getPrefix() + "&b" + player.getName() + " &7has invited you to their party. &a[Click to accept]", "/party accept " + party.getLeader().getName(), "&aClick to accept");
                                }
                                else
                                    player.sendMessage(StringUtil.CC("&c" + target.getName() + " doesn't accept party invites."));
                            }
                            else
                                player.sendMessage(StringUtil.CC("&c" + target.getName() + " is already in your party."));
                        }
                        else
                            player.sendMessage(StringUtil.CC("&cYou can't invite yourself."));
                    }
                    else
                        player.sendMessage(StringUtil.CC("&cPlayer is not online."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou can't invite players to the party."));
            }
            else
                player.sendMessage(StringUtil.CC("&cYou are not in a party."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " invite <player>"));
    }

}
