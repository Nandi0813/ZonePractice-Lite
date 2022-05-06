package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ResetArg
{

    public static void resetCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);

            if (targetProfile != null)
            {
                targetProfile.getFile().setDefaultData();
                targetProfile.getData();

                player.sendMessage(StringUtil.CC("&aYou successfully reseted " + targetProfile.getPlayer().getName() + "'s all stats."));
            }
            else
                player.sendMessage(StringUtil.CC("&cPlayer's profile cannot be found."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " reset <player>"));
    }

}
