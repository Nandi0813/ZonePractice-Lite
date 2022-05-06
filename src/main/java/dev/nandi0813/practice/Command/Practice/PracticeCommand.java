package dev.nandi0813.practice.Command.Practice;

import dev.nandi0813.practice.Command.Practice.Arguments.*;
import dev.nandi0813.practice.Manager.File.LanguageManager;
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
                player.sendMessage(LanguageManager.getString("no-permission"));
                return false;
            }

            if (args.length == 0)
                HelpArg.HelpCommand(player, label);

            else if (args[0].equalsIgnoreCase("arenas"))
                ArenasArg.ArenasCommand(player);

            else if (args[0].equalsIgnoreCase("lobby"))
                LobbyArg.LobbyCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("eloreset"))
                EloresetArg.EloResetCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("reset"))
                ResetArg.resetCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("resetall"))
                ResetallArg.ResetAllCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("endmatch"))
                EndmatchArg.MatchForceendCommand(player, label, args);

            else
                HelpArg.HelpCommand(player, label);
        }
        return true;
    }

}
