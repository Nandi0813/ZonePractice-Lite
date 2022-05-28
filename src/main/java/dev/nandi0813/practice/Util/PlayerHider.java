package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerHider implements Listener
{

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (player != p)
            {
                Profile pProfile = SystemManager.getProfileManager().getProfiles().get(p);

                if (pProfile.getStatus().equals(ProfileStatus.MATCH))
                    p.hidePlayer(player);

                if (player.hasPermission("zonepractice.staff.vanish") && !p.hasPermission("zonepractice.staff.vanish.bypass"))
                    p.hidePlayer(player);

                if (p.hasPermission("zonepractice.staff.vanish") && !player.hasPermission("zonepractice.staff.vanish.bypass"))
                    player.hidePlayer(p);
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (ServerManager.getLobby() != null)
        {
            if (e.getFrom().getWorld().equals(ServerManager.getLobby().getWorld()) && e.getTo().getWorld().equals(SystemManager.getArenaManager().getArenasWorld()))
            {
                if (profile.getStatus().equals(ProfileStatus.MATCH))
                {
                    Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);

                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        if (player != p)
                        {
                            if (!match.getPlayers().contains(p))
                                player.hidePlayer(p);
                        }
                    }
                }
                else if (profile.getStatus().equals(ProfileStatus.SPECTATE))
                {
                    Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);

                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        if (player != p)
                        {
                            if (match.getPlayers().contains(p))
                                player.showPlayer(p);
                            else if (match.getSpectators().contains(p))
                            {
                                if (ConfigManager.getBoolean("match-settings.hide-other-spectators"))
                                {
                                    player.hidePlayer(p);
                                    p.hidePlayer(player);
                                }
                                else
                                {
                                    player.showPlayer(p);
                                    p.showPlayer(player);
                                }
                            }
                        }
                    }
                }
            }
            else if (e.getFrom().getWorld().equals(SystemManager.getArenaManager().getArenasWorld()) && e.getTo().getWorld().equals(SystemManager.getArenaManager().getArenasWorld()))
            {
                if (profile.getStatus().equals(ProfileStatus.SPECTATE))
                {
                    Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);

                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        if (player != p)
                        {
                            if (match.getPlayers().contains(p))
                                player.showPlayer(p);
                            else if (match.getSpectators().contains(p))
                            {
                                if (ConfigManager.getBoolean("match-settings.hide-other-spectators"))
                                {
                                    player.hidePlayer(p);
                                    p.hidePlayer(player);
                                }
                                else
                                {
                                    player.showPlayer(p);
                                    p.showPlayer(player);
                                }
                            }
                        }
                    }
                }
            }
            else if (e.getFrom().getWorld().equals(SystemManager.getArenaManager().getArenasWorld()) && e.getTo().getWorld().equals(ServerManager.getLobby().getWorld()))
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    if (player != p)
                    {
                        if (player.hasPermission("zonepractice.staff.vanish") && !p.hasPermission("zonepractice.staff.vanish.bypass"))
                            p.hidePlayer(player);
                        else
                            p.showPlayer(player);

                        if (p.hasPermission("zonepractice.staff.vanish") && !player.hasPermission("zonepractice.staff.vanish.bypass"))
                            player.hidePlayer(p);
                        else
                            player.showPlayer(p);
                    }
                }
            }
        }
    }

}
