package dev.nandi0813.practice.Command.Staff;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!player.hasPermission("zonepractice.staff"))
            {
                player.sendMessage(StringUtil.CC("&cYou don't have permission!"));
                return false;
            }

            /*
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
            profile.setStatus(ProfileStatus.STAFFMODE);
             */
        }
        return true;
    }

}
