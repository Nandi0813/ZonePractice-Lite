package dev.nandi0813.practice.Manager.Arena;

import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena
{

    @Getter private final String name;
    @Getter @Setter private boolean available;
    @Getter @Setter private Location corner1;
    @Getter @Setter private Location corner2;
    @Getter @Setter private Cuboid cuboid;
    @Getter @Setter private Location position1;
    @Getter @Setter private Location position2;
    @Getter @Setter private Location position3;
    @Getter @Setter private List<Ladder> ladders = new ArrayList<>();
    @Getter @Setter private boolean build;
    @Getter @Setter private boolean enabled;
    @Getter private final ArenaFile file;

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
        SystemManager.getArenaManager().getArenaCuboids().remove(cuboid);
        file.getFile().delete();
    }

    public void createCuboid()
    {
        if (corner1 != null && corner2 != null)
        {
            cuboid = new Cuboid(corner1, corner2);
            SystemManager.getArenaManager().getArenaCuboids().put(cuboid, this);
        }
    }

    public Location getAvailableLocation()
    {
        if (position3 != null) return position3;
        if (position1 != null) return position1;
        if (position2 != null) return position2;
        if (corner1 != null) return corner1;
        if (corner2 != null) return corner2;
        return null;
    }

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

    public void changeBuildStatus()
    {
        setBuild(!build);
        ladders.clear();
        saveData();
    }

}
