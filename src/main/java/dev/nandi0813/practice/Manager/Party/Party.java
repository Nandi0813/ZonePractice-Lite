package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Party
{

    @Getter @Setter private Player leader;
    @Getter private final List<Player> members = new ArrayList<>();
    @Getter private final HashMap<Player, Long> invites = new HashMap<>();

    @Getter @Setter private Match match;
    @Getter @Setter private int maxPlayerLimit;

    public Party(Player owner)
    {
        this.leader = owner;
        members.add(owner);
        maxPlayerLimit = ConfigManager.getInt("party-settings.max-party-members.default");
    }

    /**
     * Sets the new owner of the party
     *
     * @param newOwner The new owner of the party.
     */
    public void setNewOwner(Player newOwner)
    {
        leader = newOwner;
        sendMessage(LanguageManager.getString("party.new-leader").replaceAll("%owner%", newOwner.getName()));
    }

    /**
     * Adds a player to the party
     *
     * @param member The player that is joining the party.
     */
    public void addMember(Player member)
    {
        Profile memberProfile = SystemManager.getProfileManager().getProfiles().get(member);

        members.add(member);
        memberProfile.setParty(true);
        SystemManager.getInventoryManager().getSpawnInventory().setInventory(member, false);

        sendMessage(LanguageManager.getString("party.player-join").replaceAll("%player%", member.getName()));
    }

    /**
     * Removes a member from the party
     *
     * @param member The player that is being removed from the party.
     * @param kick If the player is kicked or not.
     */
    public void removeMember(Player member, boolean kick)
    {
        Profile memberProfile = SystemManager.getProfileManager().getProfiles().get(member);

        if (kick)
            sendMessage(LanguageManager.getString("party.player-kick").replaceAll("%player%", member.getName()));
        else
            sendMessage(LanguageManager.getString("party.player-leave").replaceAll("%player%", member.getName()));

        if (member.equals(leader))
            disband();

        members.remove(member);
        memberProfile.setParty(false);

        if (SystemManager.getProfileManager().getProfiles().get(member).getStatus().equals(ProfileStatus.LOBBY))
            SystemManager.getInventoryManager().getSpawnInventory().setInventory(member, false);
    }

    /**
     * Disband the party, remove all members, and if the party is in a match, end the match
     */
    public void disband()
    {
        sendMessage(LanguageManager.getString("party.disband"));

        for (Player member : members)
            SystemManager.getProfileManager().getProfiles().get(member).setParty(false);

        List<Player> members_copy = new ArrayList<>(members);
        members.clear();

        if (match != null)
        {
            match.sendMessage(LanguageManager.getString("party.match-end"), true);
            match.endMatch();
        }
        else
        {
            for (Player member : members_copy)
                SystemManager.getInventoryManager().getSpawnInventory().setInventory(member, false);
        }

        SystemManager.getPartyManager().getParties().remove(this);
    }

    /**
     * If the members list is not empty, loop through each player in the members list and send them the message.
     *
     * @param message The message you want to send to the members of the party.
     */
    public void sendMessage(String message)
    {
        if (!members.isEmpty())
        {
            for (Player player : members)
                player.sendMessage(StringUtil.CC(message));
        }
    }

    /*
     * This function returns a list of the names of all the members of the clan.
     *
     * @return A list of the names of the members of the party.
     */
    public List<String> getMemberNames()
    {
        List<String> memberNames = new ArrayList<>();
        for (Player player : members)
            memberNames.add(player.getName());
        return memberNames;
    }

}
