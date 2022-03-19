package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyAcceptArg
{

    public static void AcceptCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (profile.getStatus().equals(ProfileStatus.LOBBY))
            {
                if (SystemManager.getPartyManager().getParty(player) == null)
                {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null)
                    {
                        Party party = SystemManager.getPartyManager().getParty(target);

                        if (party != null)
                        {
                            if (party.getMembers().size() < party.getMaxPlayerLimit())
                            {
                                if (party.getInvites().containsKey(player))
                                {
                                    long invitetime = party.getInvites().get(player) + (ConfigManager.getInt("party-settings.party-invite-cooldown") * 1000L);

                                    if (invitetime > System.currentTimeMillis())
                                    {
                                        party.addMember(player);
                                    }
                                    else
                                        player.sendMessage(LanguageManager.getString("party.invite-expired"));

                                    party.getInvites().remove(player);
                                }
                                else
                                    player.sendMessage(LanguageManager.getString("party.not-invited"));
                            }
                            else
                                player.sendMessage(LanguageManager.getString("party.party-full"));
                        }
                        else
                            player.sendMessage(LanguageManager.getString("party.party-doesnt-exits"));
                    }
                    else
                        player.sendMessage(LanguageManager.getString("party.player-not-online"));
                }
                else
                    player.sendMessage(LanguageManager.getString("party.already-member"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.cant-join-party"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " accept <player>"));
    }

}
