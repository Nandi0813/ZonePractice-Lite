package dev.nandi0813.practice.Command.Arena;

import dev.nandi0813.practice.Command.Arena.Arguments.*;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor
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
                ArenaHelpArg.HelpCommand(player, label);

            else if (args[0].equalsIgnoreCase("list"))
                ArenaListArg.ListCommand(player);

            else if (args[0].equalsIgnoreCase("info"))
                ArenaInfoArg.InfoCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("create"))
                ArenaCreateArg.CreateCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("delete"))
                ArenaDeleteArg.DeleteCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("setcorner"))
                ArenaSetcornerArg.SetcornerCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("setposition"))
                ArenaSetpositionArg.SetpositionCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("setbuild"))
                ArenaSetbuildArg.SetbuildCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("ladder"))
                ArenaLadderArg.LadderCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("enable"))
                ArenaEnableArg.EnableCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("disable"))
                ArenaDisableArg.DisableCommand(player, label, args);

            // Teleport player to arena positions (1/2/3)
            else if (args[0].equalsIgnoreCase("teleport"))
                ArenaTeleportArg.TeleportCommand(player, label, args);

            else
                ArenaHelpArg.HelpCommand(player, label);

        }
        return true;
    }

}
