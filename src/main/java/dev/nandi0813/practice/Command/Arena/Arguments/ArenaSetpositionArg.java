package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaSetpositionArg
{

    public static void SetpositionCommand(Player player, String label, String[] args)
    {
        if (args.length == 3)
        {
            String arenaName = args[1];
            int positionNumber = Integer.parseInt(args[2]);
            Location position = player.getLocation();
            Arena arena = Practice.getArenaManager().getArena(arenaName);

            if (arena != null)
            {
                if (arena.isEnabled())
                {
                    player.sendMessage(StringUtil.CC("&cYou can't edit a enabled arena."));
                    return;
                }

                if (arena.getCuboid() != null)
                {
                    if (!arena.getCuboid().contains(position))
                    {
                        player.sendMessage(StringUtil.CC("&cThe position must be inside the arena cube."));
                        return;
                    }

                    if (positionNumber == 1)
                    {
                        arena.setPosition1(position);
                        player.sendMessage(StringUtil.CC("&aYou saved the first &eposition &afor arena &6" + arena.getName() + "&a."));
                    }
                    else if (positionNumber == 2)
                    {
                        arena.setPosition2(position);
                        player.sendMessage(StringUtil.CC("&aYou saved the second &eposition &afor arena &6" + arena.getName() + "&a."));
                    }
                    else if (positionNumber == 3) // Spectator
                    {
                        arena.setPosition3(position);
                        player.sendMessage(StringUtil.CC("&aYou saved the &bspectator &eposition &afor arena &6" + arena.getName() + "&a."));
                    }
                    else
                    {
                        player.sendMessage(StringUtil.CC("&cInvalid number!"));
                        return;
                    }
                    arena.saveData();
                }
                else
                {
                    player.sendMessage(StringUtil.CC("&cFirst you have to set the two corner of the arena."));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setposition <name> <1/2/3>"));
        }
    }

}
