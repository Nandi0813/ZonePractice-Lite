package dev.nandi0813.practice.Command.Spectate;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectateTabCompleter implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        List<String> arguments = new ArrayList<>();
        Player player = (Player) commandSender;

        if (!player.hasPermission("zonepractice.spectate"))
            return null;


        if (args.length == 1)
        {
            for (Player online : Bukkit.getOnlinePlayers())
                if (online != player)
                    arguments.add(online.getName());
        }

        return arguments;
    }

}
