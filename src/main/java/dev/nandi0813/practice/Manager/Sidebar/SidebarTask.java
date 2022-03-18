package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SidebarTask extends BukkitRunnable implements Runnable
{

    public void begin()
    {
        this.runTaskTimerAsynchronously(Practice.getInstance(), 0, 5L);
    }

    @Override
    public void run()
    {
        for (Player player : SystemManager.getSidebarManager().getPlayerSidebars().keySet())
        {
            Sidebar sidebar = SystemManager.getSidebarManager().getPlayerSidebars().get(player);
            if (SystemManager.getProfileManager().getProfiles().get(player).isSidebar())
                sidebar.update();
        }
    }

}