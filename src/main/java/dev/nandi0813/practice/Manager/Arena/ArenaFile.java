package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaFile
{

    private final Arena arena;
    @Getter private final File file;
    private final YamlConfiguration config;

    public ArenaFile(Arena arena)
    {
        this.arena = arena;

        file = new File(Practice.getInstance().getDataFolder() + "/arenas", arena.getName().toLowerCase() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * It saves the arena's name, build status, enabled status, ladder names, and corner and position locations to the
     * config file
     */
    public void setData()
    {
        config.set("name", arena.getName());
        config.set("build", arena.isBuild());
        config.set("sumo", arena.isSumo());
        config.set("enabled", arena.isEnabled());
        config.set("ladders", ArenaUtil.getLadderNames(arena));
        if (arena.getCorner1() != null) config.set("corner.1", arena.getCorner1());
        if (arena.getCorner2() != null) config.set("corner.2", arena.getCorner2());
        if (arena.getPosition1() != null) config.set("position.1", arena.getPosition1());
        if (arena.getPosition2() != null) config.set("position.2", arena.getPosition2());
        if (arena.getPosition3() != null) config.set("position.3", arena.getPosition3());
        saveFile();
    }

    /**
     * It loads the data from the config file into the arena object
     */
    public void getData()
    {
        arena.setBuild(config.getBoolean("build"));
        arena.setSumo(config.getBoolean("sumo"));
        arena.setEnabled(config.getBoolean("enabled"));
        for (String ladderName : config.getStringList("ladders"))
        {
            Ladder ladder = Practice.getLadderManager().getLadder(ladderName);
            if (ladder != null) arena.getLadders().add(ladder);
        }
        if (config.get("corner.1") != null) arena.setCorner1((Location) config.get("corner.1"));
        if (config.get("corner.2") != null) arena.setCorner2((Location) config.get("corner.2"));
        if (config.get("position.1") != null) arena.setPosition1((Location) config.get("position.1"));
        if (config.get("position.2") != null) arena.setPosition2((Location) config.get("position.2"));
        if (config.get("position.3") != null) arena.setPosition3((Location) config.get("position.3"));
    }

    public void saveFile()
    {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
