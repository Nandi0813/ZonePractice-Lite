package dev.nandi0813.practice.Manager.Match.MatchType.Duel;

import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelListener implements Listener
{

    @EventHandler (ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            Profile profile = Practice.getProfileManager().getProfiles().get(player);
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.DUEL))
            {
                if (match.getStatus().equals(MatchStatus.LIVE))
                {
                    if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
                    {
                        e.setCancelled(true);
                        Duel.killPlayer(match, player, true);
                    }

                    if (player.getHealth() - e.getFinalDamage() <= 0)
                    {
                        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))
                            e.setCancelled(true);

                        Duel.killPlayer(match, player, false);
                    }
                }
                else
                {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.DUEL))
        {
            match.removePlayer(player, false);

            // Stop the match at the stating countdown
            if (match.getStartCountdown().isRunning())
                match.getRoundManager().endMatch(null);
            // Stop the match when it's live
            else
                match.getRoundManager().endMatch(Duel.getOppositePlayer(match, player));
        }
    }

}
