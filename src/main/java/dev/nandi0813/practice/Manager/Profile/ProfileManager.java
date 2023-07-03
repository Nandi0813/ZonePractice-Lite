package dev.nandi0813.practice.Manager.Profile;

import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class ProfileManager
{

    @Getter private final HashMap<OfflinePlayer, Profile> profiles = new HashMap<>();
    private final File folder = new File(Practice.getInstance().getDataFolder() + "/profiles");

    /**
     * If the folder exists, and the folder is a directory, and the folder has files in it, then for each file in the
     * folder, if the file is a file and the file ends with .yml, then load the file as a YamlConfiguration, get the UUID
     * from the file, get the OfflinePlayer from the UUID, create a new Profile from the OfflinePlayer, and put the
     * OfflinePlayer and Profile into the profiles HashMap
     */
    public void loadProfiles()
    {
        if (!folder.exists()) folder.mkdir();

        Bukkit.getScheduler().runTaskLaterAsynchronously(Practice.getInstance(), () ->
        {
            if (folder.isDirectory() && folder.listFiles().length > 0)
            {
                for (File profileFile : folder.listFiles())
                {
                    if (profileFile.isFile() && profileFile.getName().endsWith(".yml"))
                    {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(profileFile);
                        String uuidString = config.getString("uuid");

                        UUID uuid = UUID.fromString(uuidString);
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                        Profile profile = new Profile(uuid);
                        profile.getData();
                        profiles.put(offlinePlayer, profile);
                    }
                }
            }
        }, 20L * 2);
    }

    /**
     * It saves all the profiles
     */
    public void saveProfiles()
    {
        for (Profile profile : profiles.values()) profile.saveData();
    }

}
