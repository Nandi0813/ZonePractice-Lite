package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener
{

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Party party = SystemManager.getPartyManager().getParty(player);

        if (party != null)
        {
            party.removeMember(player, false);
        }

        if (profile != null)
        {
            profile.setStatus(ProfileStatus.OFFLINE);
            profile.saveData();
        }
        e.setQuitMessage(null);
    }

}
