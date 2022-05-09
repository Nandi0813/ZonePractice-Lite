package dev.nandi0813.practice.Command.Ladder;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LadderTabCompleter implements TabCompleter
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
            arguments.add("enable");
            arguments.add("disable");
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable"))
            {
                for (Ladder ladder : SystemManager.getLadderManager().getLadders())
                    arguments.add(ladder.getName());
            }
        }

        return arguments;
    }

}
