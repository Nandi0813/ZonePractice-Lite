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

    public void loadProfiles()
    {
        if (!folder.exists()) folder.mkdir();

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
    }

    public void saveProfiles()
    {
        for (Profile profile : profiles.values()) profile.saveData();
    }

}
