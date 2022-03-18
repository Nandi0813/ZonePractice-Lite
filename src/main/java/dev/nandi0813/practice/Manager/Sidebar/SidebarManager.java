package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class SidebarManager
{

    @Getter private final Map<Player, Sidebar> playerSidebars = new HashMap<>();
    @Getter private final SidebarTask sidebarTask = new SidebarTask();

    public SidebarManager(Practice practice)
    {
        Bukkit.getPluginManager().registerEvents(new SidebarListener(), practice);
    }

    public void enable()
    {
        for (Player player : Bukkit.getOnlinePlayers())
            loadSidebar(player);
        sidebarTask.begin();
    }

    public void disable()
    {
        for (Player player : Bukkit.getOnlinePlayers())
            unLoadSidebar(player);
    }

    public void loadSidebar(Player player)
    {
        playerSidebars.put(player, new Sidebar(player));
    }

    public void unLoadSidebar(Player player)
    {
        playerSidebars.remove(player);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        for (Team team : player.getScoreboard().getTeams())
            team.unregister();
    }



}
