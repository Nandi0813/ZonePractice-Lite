package dev.nandi0813.practice.Manager.File;

import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LadderFile
{

    private static File file;
    @Getter private static FileConfiguration config;

    public static void createFile(Practice practice)
    {
        file = new File(practice.getDataFolder(), "ladders.yml");
        if (!file.exists())
            practice.saveResource("ladders.yml", false);

        config = new YamlConfiguration();
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

    public static String getString(String loc)
    {
        return StringUtil.CC(config.getString(loc));
    }
    public static boolean getBoolean(String loc) { return config.getBoolean(loc); }
    public static int getInt(String loc) { return config.getInt(loc); }
    public static double getDouble(String loc) { return config.getDouble(loc); }
    public static Set<String> getConfigSectionKeys(String loc) { return Objects.requireNonNull(config.getConfigurationSection(loc)).getKeys(false); }
    public static List<String> getList(String loc) { return StringUtil.CC(config.getStringList(loc)); }

}
