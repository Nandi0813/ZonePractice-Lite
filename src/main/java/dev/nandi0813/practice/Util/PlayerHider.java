package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.EventPriority;

public class PlayerHider implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        // If the player is in the lobby, they see everyone except vanished players
        if (profile.getStatus().equals(ProfileStatus.LOBBY)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (player != p) {
                    if (p.hasPermission("zonepractice.staff.vanish") && !player.hasPermission("zonepractice.staff.vanish.bypass")) {
                        player.hidePlayer(p); // Hide vanished admins from regular players
                    } else {
                        player.showPlayer(p);
                    }

                    if (player.hasPermission("zonepractice.staff.vanish") && !p.hasPermission("zonepractice.staff.vanish.bypass")) {
                        p.hidePlayer(player); // Hide the player if they are vanished
                    } else {
                        p.showPlayer(player);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        // If the player becomes a spectator and enters the arena
        if (e.getFrom().getWorld().equals(ServerManager.getLobby().getWorld()) && e.getTo().getWorld().equals(Practice.getArenaManager().getArenasWorld())) {
            if (profile.getStatus().equals(ProfileStatus.SPECTATE)) {
                Match match = Practice.getMatchManager().getLiveMatchBySpectator(player);

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (player != p) {
                        player.showPlayer(p); // Spectator sees all players
                        p.hidePlayer(player); // But no one can see the spectator
                    }
                }
            }
        }

        // If the spectator remains in the arena (moving within the match)
        if (e.getFrom().getWorld().equals(Practice.getArenaManager().getArenasWorld()) && e.getTo().getWorld().equals(Practice.getArenaManager().getArenasWorld())) {
            if (profile.getStatus().equals(ProfileStatus.SPECTATE)) {
                Match match = Practice.getMatchManager().getLiveMatchBySpectator(player);

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (player != p) {
                        player.showPlayer(p); // Spectator continues seeing all players
                        p.hidePlayer(player); // But remains invisible to others
                    }
                }
            }
        }

        // If the player leaves the arena and returns to the lobby
        if (e.getFrom().getWorld().equals(Practice.getArenaManager().getArenasWorld()) && e.getTo().getWorld().equals(ServerManager.getLobby().getWorld())) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (player != p) {
                    if (p.hasPermission("zonepractice.staff.vanish") && !player.hasPermission("zonepractice.staff.vanish.bypass")) {
                        player.hidePlayer(p); // Hide vanished admins from regular players
                    } else {
                        player.showPlayer(p);
                    }

                    if (player.hasPermission("zonepractice.staff.vanish") && !p.hasPermission("zonepractice.staff.vanish.bypass")) {
                        p.hidePlayer(player); // Hide the player if they are vanished
                    } else {
                        p.showPlayer(player);
                    }
                }
            }
        }
    }
}
