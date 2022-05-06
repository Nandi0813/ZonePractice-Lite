package dev.nandi0813.practice.Command.Ladder;

import dev.nandi0813.practice.Command.Ladder.Arguments.*;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LadderCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!player.hasPermission("zonepractice.setup"))
            {
                player.sendMessage(LanguageManager.getString("no-permission"));
                return false;
            }

            if (args.length == 0)
                LadderHelpArg.HelpCommand(player, label);

            else if (args[0].equalsIgnoreCase("list"))
                LadderListArg.ListCommand(player);

            else if (args[0].equalsIgnoreCase("info"))
                LadderInfoArg.InfoCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("enable"))
                LadderEnableArg.LadderEnableCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("disable"))
                LadderDisableArg.LadderDisableCommand(player, label, args);

            else
                LadderHelpArg.HelpCommand(player, label);

        }
        return true;
    }

}
