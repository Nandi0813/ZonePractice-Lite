package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Command.Party.PartyCommand;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyManager
{

    @Getter private final List<Party> parties = new ArrayList<>();

    public PartyManager()
    {
        Bukkit.getPluginManager().registerEvents(new PartyListener(), Practice.getInstance());
    }

    public Party getParty(Player player)
    {
        for (Party party : parties)
            if (party.getMembers().contains(player))
                return party;
        return null;
    }

    public Party getParty(Match match)
    {
        for (Party party : parties)
            if (party.getMatch() != null && party.getMatch().equals(match))
                return party;
        return null;
    }

    public void createParty(Player player)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (SystemManager.getPartyManager().getParty(player) == null)
        {
            if (profile.getStatus().equals(ProfileStatus.LOBBY))
            {
                if (player.hasPermission("zonepractice.party.create"))
                {
                    Party party = new Party(player);
                    parties.add(party);
                    profile.setParty(true);

                    SystemManager.getInventoryManager().getSpawnInventory().setInventory(player, false);

                    player.sendMessage(StringUtil.CC(PartyCommand.getPrefix() + "&7You have created a party."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou don't have permission."));
            }
            else
                player.sendMessage(StringUtil.CC("&cYou can't create a party right now."));
        }
        else
            player.sendMessage(StringUtil.CC("&cYou are already in a different party."));
    }

}
