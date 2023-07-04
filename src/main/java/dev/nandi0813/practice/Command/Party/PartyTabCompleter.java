package dev.nandi0813.practice.Command.Party;

import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyTabCompleter implements TabCompleter
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
            arguments.add("create");
            arguments.add("accept");
            arguments.add("leave");
            arguments.add("info");
            arguments.add("invite");
            arguments.add("kick");
            arguments.add("leader");
            arguments.add("disband");

            StringUtil.copyPartialMatches(args[0], arguments, completion);
        }
        else if (args.length == 2)
        {
            switch (args[0])
            {
                case "invite":
                    if (Practice.getPartyManager().getParty(player) == null)
                        return arguments;

                    for (Player online : Bukkit.getOnlinePlayers())
                        if (player != online)
                            arguments.add(online.getName());

                    break;
                case "accept":
                    for (Player online : Bukkit.getOnlinePlayers())
                        if (player != online)
                            arguments.add(online.getName());
                case "kick":
                case "leader":
                    Party party = Practice.getPartyManager().getParty(player);
                    if (party == null)
                        return arguments;

                    for (Player online : party.getMembers())
                        if (player != online)
                            arguments.add(online.getName());

                    break;

            }

            StringUtil.copyPartialMatches(args[1], arguments, completion);
        }

        Collections.sort(completion);
        return completion;
    }

}
