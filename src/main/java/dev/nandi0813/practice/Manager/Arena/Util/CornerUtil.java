package dev.nandi0813.practice.Manager.Arena.Util;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

public class CornerUtil
{

    /**
     * It sets the arena corners
     *
     * @param arenaName The name of the arena you want to set the corners for.
     * @param player The player who is setting the arena corners.
     * @param corner 1 or 2
     */
    public static void setArenaCorners(String arenaName, Player player, int corner)
    {
        Arena arena = Practice.getArenaManager().getArena(arenaName);

        if (arena != null)
        {
            if (arena.isEnabled())
            {
                player.sendMessage(StringUtil.CC("&cYou can't edit a enabled arena."));
                return;
            }

            Block targetBlock = player.getTargetBlock((Set<Material>) null, 5);
            Location cornerLocation = targetBlock.getLocation();

            if (targetBlock.getType().equals(Material.AIR))
            {
                player.sendMessage(StringUtil.CC("&cBlock location can not be found!"));
                return;
            }
            if (!cornerLocation.getWorld().equals(Practice.getArenaManager().getArenasWorld()))
            {
                player.sendMessage(StringUtil.CC("&cThe corner must be located in the arenas world."));
                return;
            }

            if (corner == 1 || corner == 2)
            {
                if (corner == 1)
                {
                    arena.setCorner1(cornerLocation);
                    arena.saveData();
                    arena.createCuboid();
                    player.sendMessage(StringUtil.CC("&aYou saved the first &ecorner &afor arena &6" + arena.getName() + "&a."));
                }
                else
                {
                    arena.setCorner2(cornerLocation);
                    arena.saveData();
                    arena.createCuboid();
                    player.sendMessage(StringUtil.CC("&aYou saved the second &ecorner &afor arena &6" + arena.getName() + "&a."));
                }

                Cuboid cuboid = arena.getCuboid();
                if (arena.getPosition1() != null)
                {
                    if (!cuboid.contains(arena.getPosition1()))
                    {
                        arena.setPosition1(null);
                        arena.setEnabled(false);
                        player.sendMessage(StringUtil.CC("&cPosition 1 got removed from arena " + arena.getName() + " because it was out of the arena cube."));
                    }
                }
                if (arena.getPosition2() != null)
                {
                    if (!cuboid.contains(arena.getPosition2()))
                    {
                        arena.setPosition2(null);
                        arena.setEnabled(false);
                        player.sendMessage(StringUtil.CC("&cPosition 2 got removed from arena " + arena.getName() + " because it was out of the arena cube."));
                    }
                }
                if (arena.getPosition3() != null)
                {
                    if (!cuboid.contains(arena.getPosition3()))
                    {
                        arena.setPosition3(null);
                        arena.setEnabled(false);
                        player.sendMessage(StringUtil.CC("&cPosition 3 got removed from arena " + arena.getName() + " because it was out of the arena cube."));
                    }
                }
                arena.saveData();
            }
            else
            {
                player.sendMessage("&cInvalid number!");
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
        }
    }

}
