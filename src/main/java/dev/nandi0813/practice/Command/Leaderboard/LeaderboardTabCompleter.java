package dev.nandi0813.practice.Command.Leaderboard;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (!(commandSender instanceof Player)) return null;

        List<String> arguments = new ArrayList<>();
        List<String> completion = new ArrayList<>();

        if (args.length == 1)
        {
            for (Ladder ladder : Practice.getLadderManager().getLadders())
                if (ladder.isEnabled())
                    arguments.add(ladder.getName());

            StringUtil.copyPartialMatches(args[0], arguments, completion);
        }
        else if (args.length == 2)
        {
            Ladder ladder = Practice.getLadderManager().getLadder(args[0]);
            if (ladder != null)
            {
                arguments.add("win");
                if (ladder.isRanked())
                    arguments.add("elo");
            }

            StringUtil.copyPartialMatches(args[1], arguments, completion);
        }

        Collections.sort(completion);
        return completion;
    }

}
