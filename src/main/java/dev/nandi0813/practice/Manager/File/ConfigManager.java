package dev.nandi0813.practice.Manager.File;

import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ConfigManager
{

    private static Practice practice;
    @Getter private static FileConfiguration config;

    public static void createConfig(Practice practice)
    {
        ConfigManager.practice = practice;
        practice.saveDefaultConfig();
        practice.getConfig();
        config = practice.getConfig();
    }

    public static void saveConfig() { practice.saveConfig(); }

    public static void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(new File(practice.getDataFolder(), "config.yml"));
    }

    public static String getString(String loc)
    {
        return getConfig().getString(StringUtil.CC(loc));
    }
    public static boolean getBoolean(String loc) { return getConfig().getBoolean(loc); }
    public static int getInt(String loc) { return getConfig().getInt(loc); }
    public static double getDouble(String loc) { return getConfig().getDouble(loc); }
    public static Set<String> getConfigSectionKeys(String loc) { return Objects.requireNonNull(getConfig().getConfigurationSection(loc)).getKeys(false); }
    public static List<String> getList(String loc) { return getConfig().getStringList(loc); }

}
