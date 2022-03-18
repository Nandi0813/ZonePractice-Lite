package dev.nandi0813.practice.Command.Practice;

import dev.nandi0813.practice.Command.Practice.Arguments.ArenasArg;
import dev.nandi0813.practice.Command.Practice.Arguments.HelpArg;
import dev.nandi0813.practice.Command.Practice.Arguments.LobbyArg;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PracticeCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!player.hasPermission("zonepractice.practice"))
            {
                player.sendMessage(StringUtil.CC("&cYou don't have permission!"));
                return false;
            }

            if (args.length == 0)
                HelpArg.HelpCommand(player, label);

            else if (args[0].equalsIgnoreCase("arenas"))
                ArenasArg.ArenasCommand(player);

            else if (args[0].equalsIgnoreCase("lobby"))
                LobbyArg.LobbyCommand(player, label, args);

            else
                HelpArg.HelpCommand(player, label);
        }
        return true;
    }

}
