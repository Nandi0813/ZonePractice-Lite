package dev.nandi0813.practice.Command.Practice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PracticeTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (!(commandSender instanceof Player)) return null;

        Player player = (Player) commandSender;
        List<String> arguments = new ArrayList<>();
        List<String> completion = new ArrayList<>();

        if (!player.hasPermission("zonepractice.practice"))
            return null;


        if (args.length == 1)
        {
            arguments.add("lobby");
            arguments.add("arenas");
            arguments.add("eloreset");
            arguments.add("reset");
            arguments.add("resetall");
            arguments.add("endmatch");
            arguments.add("reload");

            StringUtil.copyPartialMatches(args[0], arguments, completion);
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("lobby"))
            {
                arguments.add("set");
            }
            else if (args[0].equalsIgnoreCase("endmatch"))
            {
                for (Player online : Bukkit.getOnlinePlayers())
                    arguments.add(online.getName());
            }

            StringUtil.copyPartialMatches(args[1], arguments, completion);
        }

        Collections.sort(completion);
        return completion;
    }

}
