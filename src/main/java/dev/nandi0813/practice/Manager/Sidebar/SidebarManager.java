package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SidebarManager {

    @Getter
    private final Map<Player, Sidebar> playerSidebars = new ConcurrentHashMap<>();
    @Getter
    private final SidebarTask sidebarTask = new SidebarTask();

    public SidebarManager(Practice practice) {
        Bukkit.getPluginManager().registerEvents(new SidebarListener(), practice);
    }

    /**
     * Enable the sidebar by loading it for all online players and starting the sidebar task.
     */
    public void enable() {
        for (Player player : Bukkit.getOnlinePlayers())
            loadSidebar(player);
        sidebarTask.begin();
    }

    /**
     * Disable the plugin by unloading all the sidebars.
     */
    public void disable() {
        for (Player player : Bukkit.getOnlinePlayers())
            unLoadSidebar(player);
    }

    /**
     * It creates a new Sidebar object for the player, and stores it in a HashMap
     *
     * @param player The player to load the sidebar for.
     */
    public void loadSidebar(Player player) {
        if (ConfigManager.getBoolean("sidebar"))
            playerSidebars.put(player, new Sidebar(player));
    }

    /**
     * It removes the sidebar from the player's scoreboard, clears the sidebar slot, and unregisters all teams
     *
     * @param player The player to unload the sidebar for.
     */
    public void unLoadSidebar(Player player) {
        playerSidebars.remove(player);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        for (Team team : player.getScoreboard().getTeams())
            team.unregister();
    }
}
