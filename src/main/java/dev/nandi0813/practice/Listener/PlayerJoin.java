package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.PlayerUtil;
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

        if (!Practice.getProfileManager().getProfiles().containsKey(player))
        {
            Profile profile = new Profile(player.getUniqueId());
            profile.getFile().setDefaultData();
            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), profile::getData, 3L);
            Practice.getProfileManager().getProfiles().put(player, profile);
        }

        if (!ConfigManager.getBoolean("multi-game-support"))
            Practice.getInventoryManager().getSpawnInventory().setInventory(player, true);

        PlayerUtil.setupPlayerRankedPerDay(player);
    }

}
