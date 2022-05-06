package dev.nandi0813.practice.Manager;

import com.comphenix.protocol.ProtocolLibrary;
import dev.nandi0813.practice.Command.Arena.ArenaCommand;
import dev.nandi0813.practice.Command.Ladder.LadderCommand;
import dev.nandi0813.practice.Command.Matchinv.MatchinvCommand;
import dev.nandi0813.practice.Command.Party.PartyCommand;
import dev.nandi0813.practice.Command.Practice.PracticeCommand;
import dev.nandi0813.practice.Command.SpectateCommand;
import dev.nandi0813.practice.Command.StatsCommand;
import dev.nandi0813.practice.Listener.*;
import dev.nandi0813.practice.Manager.Arena.ArenaManager;
import dev.nandi0813.practice.Manager.File.BackendManager;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Ladder.Custom.CustomLadderListener;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Match.MatchManager;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Manager.Queue.QueueManager;
import dev.nandi0813.practice.Manager.Sidebar.SidebarManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.Enderpearl.EnderpearlListener;
import dev.nandi0813.practice.Util.EntityHider.EntityHider;
import dev.nandi0813.practice.Util.EntityHider.EntityHiderListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class SystemManager
{

    @Getter private static final ArenaManager arenaManager = new ArenaManager();
    @Getter private static final LadderManager ladderManager = new LadderManager();
    @Getter private static final ProfileManager profileManager = new ProfileManager();
    @Getter private static final MatchManager matchManager = new MatchManager(Practice.getInstance());
    @Getter private static final QueueManager queueManager = new QueueManager(Practice.getInstance());
    @Getter private static final PartyManager partyManager = new PartyManager();
    @Getter private static final InventoryManager inventoryManager = new InventoryManager();
    @Getter private static final SidebarManager sidebarManager = new SidebarManager(Practice.getInstance());
    @Getter private static final EntityHider entityHider = new EntityHider(Practice.getInstance(), EntityHider.Policy.BLACKLIST);

    public static void Enable(Practice practice)
    {
        ConfigManager.createConfig(practice);
        LanguageManager.createFile(practice);
        BackendManager.createFile(practice);
        ladderManager.loadLadders();
        arenaManager.loadArenas();
        profileManager.loadProfiles();
        sidebarManager.enable();

        registerCommands();
        registerListeners(practice, Bukkit.getPluginManager());
        registerPacketListener();
    }

    public static void Disable()
    {
        profileManager.saveProfiles();
        matchManager.disableLiveMatches();
        sidebarManager.disable();
        entityHider.close();
    }

    public static void registerCommands()
    {
        Bukkit.getServer().getPluginCommand("arena").setExecutor(new ArenaCommand());
        Bukkit.getServer().getPluginCommand("ladder").setExecutor(new LadderCommand());
        Bukkit.getServer().getPluginCommand("practice").setExecutor(new PracticeCommand());
        Bukkit.getServer().getPluginCommand("spectate").setExecutor(new SpectateCommand());
        Bukkit.getServer().getPluginCommand("party").setExecutor(new PartyCommand());
        Bukkit.getServer().getPluginCommand("matchinv").setExecutor(new MatchinvCommand());
        Bukkit.getServer().getPluginCommand("statistics").setExecutor(new StatsCommand());
    }

    public static void registerListeners(Practice practice, PluginManager pm)
    {
        pm.registerEvents(new PlayerJoin(), practice);
        pm.registerEvents(new PlayerQuit(), practice);
        pm.registerEvents(new PlayerInteract(), practice);
        pm.registerEvents(new InventoryClick(), practice);
        pm.registerEvents(new EnderpearlListener(), practice);
        pm.registerEvents(new WeatherChange(), practice);
        pm.registerEvents(new ItemConsume(), practice);
        pm.registerEvents(new ProjectileLaunch(), practice);
        pm.registerEvents(new EntityHiderListener(), practice);
        pm.registerEvents(new PlayerCommandPreprocess(), practice);
        pm.registerEvents(new CustomLadderListener(), practice);
    }

    public static void registerPacketListener()
    {
        try
        {
            ProtocolLibrary.getProtocolManager().addPacketListener(new EntityHiderListener());
        }
        catch (Exception e) { e.printStackTrace(); }
    }

}
