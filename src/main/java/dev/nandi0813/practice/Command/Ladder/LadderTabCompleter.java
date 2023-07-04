package dev.nandi0813.practice.Command.Ladder;

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

public class LadderTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (!(commandSender instanceof Player)) return null;

        Player player = (Player) commandSender;

        if (!player.hasPermission("zonepractice.setup"))
            return null;

        List<String> arguments = new ArrayList<>();
        List<String> completion = new ArrayList<>();

        if (args.length == 1)
        {
            arguments.add("setname");
            arguments.add("seticon");
            arguments.add("setinv");
            arguments.add("setcombo");
            arguments.add("hitdelay");
            arguments.add("setranked");
            arguments.add("seteditable");
            arguments.add("setregen");
            arguments.add("sethunger");
            arguments.add("setbuild");
            arguments.add("setenable");
            arguments.add("list");
            arguments.add("info");

            StringUtil.copyPartialMatches(args[0], arguments, completion);
        }
        else if (args.length == 2)
        {
            switch (args[0])
            {
                case "info":
                case "setname":
                case "seticon":
                case "setinv":
                case "setcombo":
                case "hitdelay":
                case "setranked":
                case "seteditable":
                case "setregen":
                case "sethunger":
                case "setbuild":
                case "setenable":
                    for (Ladder ladder : Practice.getLadderManager().getLadders())
                    {
                        arguments.add(String.valueOf(ladder.getId()));
                        if (ladder.getName() != null)
                            arguments.add(ladder.getName());
                    }
                    break;
            }

            StringUtil.copyPartialMatches(args[1], arguments, completion);
        }

        Collections.sort(completion);
        return completion;
    }

}
