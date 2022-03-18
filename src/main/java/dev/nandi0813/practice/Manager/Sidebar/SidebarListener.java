package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SidebarListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () ->
        {
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
            if (profile.isSidebar())
                SystemManager.getSidebarManager().loadSidebar(player);
        }, 5L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.isSidebar())
            SystemManager.getSidebarManager().unLoadSidebar(player);
    }

}
