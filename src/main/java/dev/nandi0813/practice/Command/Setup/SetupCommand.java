package dev.nandi0813.practice.Command.Setup;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!player.hasPermission("zonepractice.setup"))
            {
                player.sendMessage(StringUtil.CC("&cYou don't have permission!"));
                return false;
            }



        }
        return true;
    }

}
