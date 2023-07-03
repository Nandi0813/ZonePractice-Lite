package dev.nandi0813.practice.Manager.Match.MatchStats;

import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class MatchStatListener implements Listener
{

    @Getter private final Practice practice;
    @Getter private static final HashMap<Player, Integer> currentCPS = new HashMap<>();
    @Getter private static final HashMap<Player, Integer> currentCombo = new HashMap<>();


    public MatchStatListener(Practice practice)
    {
        this.practice = practice;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.FISHING_ROD) return;

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getStatus().equals(MatchStatus.LIVE) && match.getType().equals(MatchType.DUEL))
        {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                if (!currentCPS.containsKey(player))
                {
                    currentCPS.put(player, 1);

                    BukkitRunnable task = new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if (currentCPS.containsKey(player))
                            {
                                int current = currentCPS.get(player);

                                if (current > 2)
                                {
                                    match.getMatchStats().get(player).getCps().put(System.currentTimeMillis(), current);
                                }
                                currentCPS.remove(player);
                            }
                        }
                    };
                    task.runTaskLaterAsynchronously(Practice.getInstance(), 20L);
                }
                else
                    currentCPS.put(player, currentCPS.get(player) + 1);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerHit(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Player defender = (Player) e.getEntity();

            Bukkit.getScheduler().runTaskAsynchronously(practice, () ->
            {
                if (Practice.getProfileManager().getProfiles().get(attacker).getStatus().equals(ProfileStatus.MATCH) && Practice.getProfileManager().getProfiles().get(defender).getStatus().equals(ProfileStatus.MATCH))
                {
                    Match match = Practice.getMatchManager().getLiveMatchByPlayer(attacker);

                    if (match.getStatus().equals(MatchStatus.LIVE) && match.getType().equals(MatchType.DUEL))
                    {
                        PlayerMatchStat attackerStats = match.getMatchStats().get(attacker);
                        PlayerMatchStat defenderStats = match.getMatchStats().get(defender);

                        // Hit and Get hit stats
                        attackerStats.setHit(attackerStats.getHit() + 1);
                        defenderStats.setGetHit(defenderStats.getGetHit() + 1);

                        // Combo stats
                        if (currentCombo.containsKey(attacker))
                            currentCombo.put(attacker, currentCombo.get(attacker) + 1);
                        else
                            currentCombo.put(attacker, 1);
                        // match.sendMessage("&c" + attacker.getName() + " &7combo: &e" + currentCombo.get(attacker), false);


                        if (currentCombo.containsKey(defender))
                        {
                            if (defenderStats.getLongestCombo() < currentCombo.get(defender))
                            {
                                defenderStats.setLongestCombo(currentCombo.get(defender));
                                // match.sendMessage("&c" + defender.getName() + " &7has a new combo record: &e" + currentCombo.get(defender), false);
                            }
                        }
                        currentCombo.put(defender, 0);
                    }
                }
            });
        }
    }

}
