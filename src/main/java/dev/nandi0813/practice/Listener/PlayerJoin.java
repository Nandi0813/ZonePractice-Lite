package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        final Player player = e.getPlayer();
        e.setJoinMessage(null);

        if (!SystemManager.getProfileManager().getProfiles().containsKey(player))
        {
            Profile profile = new Profile(player.getUniqueId());
            profile.getFile().setDefaultData();
            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), profile::getData, 3L);
            SystemManager.getProfileManager().getProfiles().put(player, profile);
        }

        SystemManager.getInventoryManager().getSpawnInventory().setInventory(player, true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            if (onlinePlayer != player)
            {
                Profile profile = SystemManager.getProfileManager().getProfiles().get(onlinePlayer);

                if (profile.getStatus().equals(ProfileStatus.MATCH))
                    onlinePlayer.hidePlayer(player);
            }
        }
    }

}
