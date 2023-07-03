package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaCreateArg
{

    public static void CreateCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            if (Practice.getArenaManager().getArenas().size() < 243)
            {
                String arenaName = args[1];
                if (Practice.getArenaManager().getArena(arenaName) == null)
                {
                    Arena arena = new Arena(arenaName);
                    Practice.getArenaManager().getArenas().add(arena);
                    arena.saveData();
                    player.sendMessage(StringUtil.CC("&e" + arenaName + " &aarena has been created."));
                }
                else
                {
                    player.sendMessage(StringUtil.CC("&c" + arenaName + " arena already exists."));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC("&cYou can't create more than 243 arenas."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " create <name>"));
        }
    }

}
