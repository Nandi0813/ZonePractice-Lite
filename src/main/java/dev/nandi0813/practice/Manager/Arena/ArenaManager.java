package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.BlockChange.RollbackListener;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ArenaManager
{

    @Getter private World arenasWorld;
    @Getter private final List<Arena> arenas = new ArrayList<>();
    @Getter private final HashMap<Cuboid, Arena> arenaCuboids = new HashMap<>();
    @Getter private final File folder = new File(Practice.getInstance().getDataFolder() + "/arenas");

    public ArenaManager()
    {
        Bukkit.getPluginManager().registerEvents(new RollbackListener(Practice.getInstance()), Practice.getInstance());

        arenasWorld = Bukkit.getWorld("arenas");
        createArenaWorld();
    }

    /**
     * Return the arena with the given name, or null if no arena with that name exists.
     *
     * @param arenaName The name of the arena you want to get.
     * @return The arena with the name that is passed in.
     */
    public Arena getArena(String arenaName)
    {
        for (Arena arena : arenas)
        {
            if (arena.getName().equalsIgnoreCase(arenaName))
            {
                return arena;
            }
        }
        return null;
    }

    /**
     * This function returns an ArrayList of all the enabled arenas.
     *
     * @return An ArrayList of enabled arenas.
     */
    public ArrayList<Arena> getEnabledArenas()
    {
        ArrayList<Arena> enabledArenas = new ArrayList<>();
        for (Arena arena : arenas)
            if (arena.isEnabled()) enabledArenas.add(arena);
        return enabledArenas;
    }

    /**
     * Get all enabled arenas that are available and have the same build status as the parameter.
     *
     * @param build If true, only arenas that are built will be returned. If false, only arenas that are not built will be
     * returned.
     * @return An ArrayList of Arena objects.
     */
    public ArrayList<Arena> getAvailableArenas(boolean build, boolean sumo)
    {
        ArrayList<Arena> availableArenas = new ArrayList<>();
        for (Arena arena : getEnabledArenas())
            if (arena.isAvailable() && (
                    arena.isBuild() == build &&
                    arena.isSumo() == sumo
            ))
                availableArenas.add(arena);
        return availableArenas;
    }

    /**
     * "Get a random arena from the list of available arenas."
     *
     * The first line of the function is a comment. Comments are ignored by the compiler, but they are useful for
     * explaining what the code does
     *
     * @param build If true, the arena will be built if it's not already built.
     * @return A random arena from the list of available arenas.
     */
    public Arena getRandomArena(boolean build, boolean sumo)
    {
        Random random = new Random();
        ArrayList<Arena> availableArenas = getAvailableArenas(build, sumo);
        if (availableArenas.size() > 0)
            return availableArenas.get(random.nextInt(availableArenas.size()));
        else
            return null;
    }

    /**
     * If the folder exists, and there are files in the folder, then for each file in the folder, if the file is a file and
     * ends with ".yml", then load the file as a YamlConfiguration, get the name of the arena from the file, and add a new
     * Arena to the list of arenas
     */
    public void loadArenas()
    {
        if (!folder.exists()) folder.mkdir();

        if (folder.isDirectory() && folder.listFiles().length > 0)
        {
            for (File arenaFile : folder.listFiles())
            {
                if (arenaFile.isFile() && arenaFile.getName().endsWith(".yml"))
                {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(arenaFile);
                    String name = config.getString("name");
                    arenas.add(new Arena(name));
                }
            }
        }
    }

    public void saveArenas()
    {
        for (Arena arena : arenas)
            arena.saveData();
    }

    /**
     * Create a new world called "arenas" with the world type "FLAT" and the generator settings "2;0;1;"
     */
    public void createArenaWorld()
    {
        if (arenasWorld == null)
        {
            WorldCreator wc = new WorldCreator(ConfigManager.getConfig().getString("arena-world-name"));
            wc.type(WorldType.FLAT);
            wc.generatorSettings("2;0;1;");
            wc.createWorld();
            arenasWorld = Bukkit.getWorld("arenas");
            ArenaUtil.setGamerules(arenasWorld);
        }
    }

}
