package dev.nandi0813.practice.Manager.Match.MatchType.PartySplit;

import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Util.TeamUtil;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.List;

public class PartySplitListener implements Listener
{

    @EventHandler (ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
            Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT))
            {
                if (match.getStatus().equals(MatchStatus.LIVE) && match.getAlivePlayers().contains(player))
                {
                    if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
                    {
                        e.setCancelled(true);
                        PartySplit.killPlayer(match, player, true);
                    }

                    if (player.getHealth() - e.getFinalDamage() <= 0)
                    {
                        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))
                            e.setCancelled(true);

                        PartySplit.killPlayer(match, player, true);
                    }
                }
                else
                {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (ConfigManager.getBoolean("match-settings.party.split-team-damage")) return;

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Profile attackerProfile = SystemManager.getProfileManager().getProfiles().get(attacker);
            Player target = (Player) e.getEntity();
            Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);

            if (attackerProfile.getStatus().equals(ProfileStatus.MATCH) && targetProfile.getStatus().equals(ProfileStatus.MATCH))
            {
                Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(attacker);
                if (match.getType().equals(MatchType.PARTY_SPLIT) && match.equals(SystemManager.getMatchManager().getLiveMatchByPlayer(target)))
                {
                    if (match.getTeams().get(attacker).equals(match.getTeams().get(target)))
                    {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT))
        {
            match.sendMessage("&e" + player.getName() + " &7has left the match.", true);

            match.removePlayer(player);
            match.getPlayers().remove(player);
            match.getAlivePlayers().remove(player);

            TeamEnum team1 = match.getTeams().get(player);
            List<Player> team1Players = PartySplit.getTeamPlayers(match, match.getTeams().get(player));
            List<Player> team2Players = PartySplit.getTeamPlayers(match, TeamUtil.getOppositeTeam(match.getTeams().get(player)));
            match.getTeams().remove(player);
            team1Players.remove(player);

            if (match.getPlayers().size() < 2)
            {
                for (Player winner : match.getPlayers())
                {
                    match.getRoundManager().endMatch(winner);
                    return;
                }
            }
            else if (PartySplit.getTeamAlivePlayers(match, TeamEnum.TEAM1).isEmpty())
            {
                match.getRoundManager().endRound(match.getAlivePlayers().stream().findAny().get());
            }

            // Team equalizer
            if (match.getAfterCountdown().isRunning())
            {
                if (team1Players.size() + 1 < team2Players.size())
                {
                    Collections.shuffle(team2Players);
                    for (Player team2Player : team2Players)
                    {
                        match.getTeams().put(team2Player, team1);

                        match.sendMessage("&e" + team2Player.getName() + " &cwas moved to " + team1.getName() + " &cto even the match.", true);
                        return;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT) && !match.getAlivePlayers().contains(player))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickItem(PlayerPickupItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT) && !match.getAlivePlayers().contains(player))
            e.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT) && !match.getAlivePlayers().contains(player))
            e.setFoodLevel(20);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player player = (Player) e.getDamager();
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
            Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT) && !match.getAlivePlayers().contains(player))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getType().equals(MatchType.PARTY_SPLIT) && !match.getAlivePlayers().contains(player))
        {
            Cuboid cuboid = match.getGameArena().getCuboid();

            if (!cuboid.contains(e.getTo()))
                player.teleport(match.getGameArena().getPosition3());
        }
    }

}