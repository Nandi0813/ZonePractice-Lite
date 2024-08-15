package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Arena
{

    private final String name;
    private boolean available;
    private Location corner1;
    private Location corner2;
    private Cuboid cuboid;
    private Location position1;
    private Location position2;
    private Location position3;
    private List<Ladder> ladders = new ArrayList<>();
    private boolean build;
    private boolean sumo;
    private boolean enabled;
    private final ArenaFile file;

    public Arena(String name)
    {
        this.name = name;
        available = true;

        file = new ArenaFile(this);
        file.getData();

        createCuboid();
    }

    public void saveData()
    {
        file.setData();
    }

    public void deleteData()
    {
        Practice.getArenaManager().getArenaCuboids().remove(cuboid);
        file.getFile().delete();
    }

    /**
     * If the two corners are not null, create a new cuboid with the two corners and add it to the arena cuboids map.
     */
    public void createCuboid()
    {
        if (corner1 != null && corner2 != null)
        {
            cuboid = new Cuboid(corner1, corner2);
            Practice.getArenaManager().getArenaCuboids().put(cuboid, this);
        }
    }

    /**
     * If position3 is not null, return position3. Otherwise, if position1 is not null, return position1. Otherwise, if
     * position2 is not null, return position2. Otherwise, if corner1 is not null, return corner1. Otherwise, if corner2 is
     * not null, return corner2. Otherwise, return null.
     *
     * @return A location
     */
    public Location getAvailableLocation()
    {
        if (position3 != null) return position3;
        if (position1 != null) return position1;
        if (position2 != null) return position2;
        if (corner1 != null) return corner1;
        if (corner2 != null) return corner2;
        return null;
    }

    /**
     * If the arena has an available location, set the player's gamemode to creative, set the player's flying to true,
     * teleport the player to the available location, and close the player's inventory
     *
     * @param player The player to teleport.
     */
    public void teleport(Player player)
    {
        if (getAvailableLocation() != null)
        {
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
            player.teleport(getAvailableLocation());
            player.closeInventory();
        }
        else player.sendMessage(StringUtil.CC("&cArena has no available location."));
    }

    /**
     * It sets the build boolean to the opposite of what it was, clears the ladders list, and saves the data
     */
    public void changeBuildStatus()
    {
        setBuild(!build);
        ladders.clear();
        saveData();
    }

    /**
     * It sets the sumo boolean to the opposite of what it was, clears the ladders list, and saves the data
     */
    public void changeSumoStatus()
    {
        setSumo(!sumo);
        ladders.clear();
        saveData();
    }

}
