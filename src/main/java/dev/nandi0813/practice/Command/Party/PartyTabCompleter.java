package dev.nandi0813.practice.Command.Party;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PartyTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        List<String> arguments = new ArrayList<>();

        if (args.length == 1)
        {
            arguments.add("create");
            arguments.add("accept");
            arguments.add("leave");
            arguments.add("info");
            arguments.add("invite");
            arguments.add("kick");
            arguments.add("leader");
            arguments.add("disband");
        }

        return arguments;
    }

}
