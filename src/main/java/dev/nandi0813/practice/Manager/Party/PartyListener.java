package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PartyListener implements Listener
{

    /**
     * When a player quits, if they are in a party, remove them from the party
     *
     * @param e The event that is being listened for.
     */
    @EventHandler (ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Party party = Practice.getPartyManager().getParty(player);

        if (party != null)
        {
            party.removeMember(player, false);
        }
    }

}
