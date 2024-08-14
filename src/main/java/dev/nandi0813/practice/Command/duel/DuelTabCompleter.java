package dev.nandi0813.practice.Command.duel;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuelTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (!(commandSender instanceof Player)) return null;

        Player player = (Player) commandSender;
        List<String> arguments = new ArrayList<>();
        List<String> completion = new ArrayList<>();


        if (args.length == 1)
        {
            for (Player online : Bukkit.getOnlinePlayers())
                if (online != player)
                    arguments.add(online.getName());

            StringUtil.copyPartialMatches(args[0], arguments, completion);
        }

        Collections.sort(completion);
        return completion;
    }

}
