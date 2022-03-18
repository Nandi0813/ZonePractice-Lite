package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.BlockChange.RollbackListener;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
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

    public Arena getArena(Arena arenaID)
    {
        for (Arena arena : arenas)
        {
            if (arena.equals(arenaID))
            {
                return arena;
            }
        }
        return null;
    }

    public ArrayList<Arena> getEnabledArenas()
    {
        ArrayList<Arena> enabledArenas = new ArrayList<>();
        for (Arena arena : arenas)
            if (arena.isEnabled()) enabledArenas.add(arena);
        return enabledArenas;
    }

    public ArrayList<Arena> getAvailableArenas(boolean build)
    {
        ArrayList<Arena> availableArenas = new ArrayList<>();
        for (Arena arena : getEnabledArenas())
            if (arena.isAvailable() && arena.isBuild() == build)
                availableArenas.add(arena);
        return availableArenas;
    }

    public Arena getRandomArena(boolean build)
    {
        Random random = new Random();
        ArrayList<Arena> availableArenas = getAvailableArenas(build);
        if (availableArenas.size() > 0)
            return availableArenas.get(random.nextInt(availableArenas.size()));
        else
            return null;
    }

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

    public void createArenaWorld()
    {
        if (arenasWorld == null)
        {
            WorldCreator wc = new WorldCreator("arenas");
            wc.type(WorldType.FLAT);
            wc.generatorSettings("2;0;1;");
            wc.createWorld();
            arenasWorld = Bukkit.getWorld("arenas");
            ArenaUtil.setGamerules(arenasWorld);
        }
    }

}
