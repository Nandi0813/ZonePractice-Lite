package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SidebarTask extends BukkitRunnable implements Runnable
{

    public void begin()
    {
        this.runTaskTimerAsynchronously(Practice.getInstance(), 0, 5L);
    }

    /**
     * It updates all the sidebars
     */
    @Override
    public void run()
    {
        for (Player player : Practice.getSidebarManager().getPlayerSidebars().keySet())
        {
            Sidebar sidebar = Practice.getSidebarManager().getPlayerSidebars().get(player);
            if (sidebar != null)
                sidebar.update();
        }
    }

}
