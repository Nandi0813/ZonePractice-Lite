package dev.nandi0813.practice.Command.Leaderboard;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        List<String> arguments = new ArrayList<>();

        if (args.length == 1)
        {
            for (Ladder ladder : Practice.getLadderManager().getLadders())
                if (ladder.isEnabled())
                    arguments.add(ladder.getName());
        }
        else if (args.length == 2)
        {
            arguments.add("win");
            arguments.add("elo");
        }

        return arguments;
    }

}
