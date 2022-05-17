package dev.nandi0813.practice.Manager.Arena.Util;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaUtil
{

    /**
     * This function returns a list of strings that are the names of the ladders in the given arena.
     *
     * @param arena The arena you want to get the ladders from.
     * @return A list of strings containing the names of the ladders in the arena.
     */
    public static List<String> getLadderNames(Arena arena)
    {
        List<String> ladderStrings = new ArrayList<>();
        for (Ladder ladder : arena.getLadders()) ladderStrings.add(ladder.getName());
        return ladderStrings;
    }

    /**
     * If the arena is disabled, check if all the corners and positions are set, if they are, enable the arena. If the
     * arena is enabled, check if there's a match in the arena, if there isn't, disable the arena
     *
     * @param player The player who is changing the arena's status.
     * @param arena The arena you want to change the status of.
     */
    public static void changeStatus(Player player, Arena arena)
    {
        if (!arena.isEnabled())
        {
            if (arena.getCorner1() != null && arena.getCorner2() != null && arena.getPosition1() != null && arena.getPosition2() != null && arena.getPosition3() != null)
            {
                arena.setEnabled(true);
                arena.saveData();
                player.sendMessage(StringUtil.CC("&eYou successfully &aenabled &earena &7" + arena.getName() + "&e."));
            }
            else
            {
                player.sendMessage(StringUtil.CC("&cThe arena doesn't meet all the requirements. You have to set all the corners, positions."));
            }
        }
        else
        {
            if (SystemManager.getMatchManager().getLiveMatchByArena(arena) == null)
            {
                arena.setEnabled(false);
                arena.saveData();
                player.sendMessage(StringUtil.CC("&eYou successfully &cdisabled &earena &7" + arena.getName() + "&e."));
            }
            else
            {
                player.sendMessage(StringUtil.CC("&cYou can't disable a arena that currently has a match in it."));
            }
        }
    }

    /**
     * It sets the spawn location, disables the day/night cycle, disables mob spawning, disables death messages, and
     * disables fire spread
     *
     * @param world The world you want to set the gamerules for.
     */
    public static void setGamerules(World world)
    {
        world.setSpawnLocation(0, 60, 0);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("doFireTick", "false");
    }

}
