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

public class LanguageManager
{

    private static File file;
    @Getter private static FileConfiguration config;

    public static void createFile(Practice practice)
    {
        file = new File(practice.getDataFolder(), "language.yml");
        if (!file.exists())
            practice.saveResource("language.yml", false);

        config= new YamlConfiguration();
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
        return getConfig().getString(StringUtil.CC(loc));
    }
    public static boolean getBoolean(String loc) { return getConfig().getBoolean(loc); }
    public static int getInt(String loc) { return getConfig().getInt(loc); }
    public static double getDouble(String loc) { return getConfig().getDouble(loc); }
    public static Set<String> getConfigSectionKeys(String loc) { return Objects.requireNonNull(getConfig().getConfigurationSection(loc)).getKeys(false); }
    public static List<String> getList(String loc) { return StringUtil.CC(getConfig().getStringList(loc)); }

}
