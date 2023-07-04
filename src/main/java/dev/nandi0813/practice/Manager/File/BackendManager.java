package dev.nandi0813.practice.Manager.File;

import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BackendManager
{

    private static File file;
    @Getter private static FileConfiguration config;

    public static void createFile(Practice practice)
    {
        file = new File(practice.getDataFolder(), "backend.yml");
        config = YamlConfiguration.loadConfiguration(file);
        save();
        reload();
    }

    public static void save()
    {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload()
    {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
