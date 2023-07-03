package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ResetallArg
{

    public static void ResetAllCommand(Player player, String label, String[] args)
    {
        if (args.length == 1)
        {
            if (player.hasPermission("zonepractice.stats.resetall"))
            {
                Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
                {
                    for (Profile profile : Practice.getProfileManager().getProfiles().values())
                    {
                        profile.getFile().setDefaultData();
                        profile.getData();
                    }
                });

                player.sendMessage(StringUtil.CC("&aYou successfully reseted all player stats!"));
            }
            else
                player.sendMessage(LanguageManager.getString("no-permission"));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " resetall"));
    }

}
