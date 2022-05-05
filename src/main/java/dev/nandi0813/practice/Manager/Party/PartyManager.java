package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
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

    /**
     * If the player is in the lobby, and they don't have a party, create a new party and add them to it
     *
     * @param player The player who is creating the party.
     */
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

                    player.sendMessage(LanguageManager.getString("party.create"));
                }
                else
                    player.sendMessage(LanguageManager.getString("no-permission"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.cant-create"));
        }
        else
            player.sendMessage(LanguageManager.getString("party.already-member"));
    }

}
