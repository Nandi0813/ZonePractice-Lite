package dev.nandi0813.practice.Command.Ladder;

import dev.nandi0813.practice.Command.Ladder.Arguments.*;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LadderCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (!player.hasPermission("zonepractice.setup"))
        {
            player.sendMessage(LanguageManager.getString("no-permission"));
            return false;
        }

        if (args.length > 0)
        {
            switch (args[0])
            {
                case "setname":
                    SetNameArg.run(player, label, args);
                    break;
                case "seticon":
                    SetIconArg.run(player, label, args);
                    break;
                case "setinventory":
                case "setinv":
                    InventoryArg.run(player, label, args);
                    break;
                case "setcombo":
                    SetCombo.run(player, label, args);
                    break;
                case "hitdelay":
                    SetHitdelay.run(player, label, args);
                    break;
                case "setranked":
                    SetRanked.run(player, label, args);
                    break;
                case "seteditable":
                    SetEditable.run(player, label, args);
                    break;
                case "setregen":
                    SetRegen.run(player, label, args);
                    break;
                case "sethunger":
                    SetHunger.run(player, label, args);
                    break;
                case "setbuild":
                    SetBuild.run(player, label, args);
                    break;
                case "list":
                    ListArg.run(player, label, args);
                    break;
                case "info":
                    InfoArg.run(player, label, args);
                    break;
                case "setenable":
                    EnableArg.run(player, label, args);
                    break;
                default:
                    HelpArg.run(player, label);
            }
        }
        else
            HelpArg.run(player, label);

        return true;
    }

}
