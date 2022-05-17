package dev.nandi0813.practice.Manager.Server;

import dev.nandi0813.practice.Manager.File.BackendManager;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ServerManager
{

    /**
     * It sets the lobby location to the location passed in
     *
     * @param lobbyLocation The location of the lobby
     */
    public static void setLobby(Location lobbyLocation)
    {
        BackendManager.getConfig().set("lobby", lobbyLocation);
        BackendManager.save();
    }

    /**
     * If the config has a lobby location, return it, otherwise return null
     *
     * @return The lobby location
     */
    public static Location getLobby()
    {
        if (BackendManager.getConfig().get("lobby") != null) return (Location) BackendManager.getConfig().get("lobby");
        else return null;
    }

}
