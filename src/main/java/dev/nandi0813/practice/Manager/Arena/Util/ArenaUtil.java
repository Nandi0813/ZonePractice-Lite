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

    public static List<String> getLadderNames(Arena arena)
    {
        List<String> ladderStrings = new ArrayList<>();
        for (Ladder ladder : arena.getLadders()) ladderStrings.add(ladder.getName());
        return ladderStrings;
    }

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

    public static void setGamerules(World world)
    {
        world.setSpawnLocation(0, 60, 0);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("doFireTick", "false");
    }

}
