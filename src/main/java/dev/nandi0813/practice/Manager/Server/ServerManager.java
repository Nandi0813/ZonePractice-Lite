package dev.nandi0813.practice.Manager.Server;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ServerManager
{

    @Getter private static final HashMap<Player, ItemStack[]> inventories = new HashMap<>();
    @Getter private static final HashMap<Player, ItemStack[]> armors = new HashMap<>();

    public static void setLobby(Location lobbyLocation)
    {
        ConfigManager.getConfig().set("lobby", lobbyLocation);
        ConfigManager.saveConfig();
    }

    public static Location getLobby()
    {
        if (ConfigManager.getConfig().get("lobby") != null) return (Location) ConfigManager.getConfig().get("lobby");
        else return null;
    }

}
