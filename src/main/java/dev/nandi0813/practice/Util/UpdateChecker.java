package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker
{

    private final Practice plugin;
    private final int resourceId;

    public UpdateChecker(Practice plugin, int resourceId)
    {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream))
            {
                if (scanner.hasNext())
                    consumer.accept(scanner.next());
            }
            catch (IOException exception)
            {
                plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }


    public static void check(Practice practice)
    {
        new UpdateChecker(practice, 101928).getVersion(version ->
        {
            if (!practice.getDescription().getVersion().equals(version))
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aThere is a new update available for &6ZonePractice Lite&a.\n&aYou can download it from: https://www.spigotmc.org/resources/zonepractice-lite-the-free-practice-plugin-1v1-parties-build-fights-and-more.101928/"));
        });
    }

}
