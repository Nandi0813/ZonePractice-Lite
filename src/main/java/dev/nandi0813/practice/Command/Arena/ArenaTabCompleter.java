package dev.nandi0813.practice.Command.Arena;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        List<String> arguments = new ArrayList<>();
        Player player = (Player) commandSender;

        if (!player.hasPermission("zonepractice.setup"))
            return null;


        if (args.length == 1)
        {
            arguments.add("list");
            arguments.add("info");
            arguments.add("create");
            arguments.add("delete");
            arguments.add("enable");
            arguments.add("disable");
            arguments.add("setcorner");
            arguments.add("setposition");
            arguments.add("setbuild");
            arguments.add("ladder");
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("setcorner") || args[0].equalsIgnoreCase("setposition") || args[0].equalsIgnoreCase("setbuild"))
            {
                for (Arena arena : Practice.getArenaManager().getArenas())
                    arguments.add(arena.getName());
            }
            else if (args[0].equalsIgnoreCase("ladder"))
            {
                arguments.add("list");
                arguments.add("add");
                arguments.add("remove");
            }
        }
        else if (args.length == 3)
        {
            if (args[0].equalsIgnoreCase("setcorner"))
            {
                arguments.add("1");
                arguments.add("2");
            }
            else if (args[0].equalsIgnoreCase("setposition"))
            {
                arguments.add("1");
                arguments.add("2");
                arguments.add("3");
            }
            else if (args[0].equalsIgnoreCase("setbuild"))
            {
                arguments.add("true");
                arguments.add("false");
            }
            else if (args[0].equalsIgnoreCase("ladder"))
            {
                if (args[1].equalsIgnoreCase("list"))
                {
                    for (Arena arena : Practice.getArenaManager().getArenas())
                        arguments.add(arena.getName());
                }
                else if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))
                {
                    for (Arena arena : Practice.getArenaManager().getArenas())
                        arguments.add(arena.getName());
                }
            }
        }
        else if (args.length == 4)
        {
            if (args[0].equalsIgnoreCase("ladder") && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")))
            {
                for (Ladder ladder : Practice.getLadderManager().getLadders())
                    arguments.add(ladder.getName());
            }
        }

        return arguments;
    }

}
