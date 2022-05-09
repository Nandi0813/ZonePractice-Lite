package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ReloadArg
{

    public static void ReloadCommand(Player player)
    {
        if (player.hasPermission("zonepractice.reload"))
        {
            ConfigManager.reloadConfig();
            LanguageManager.reload();

            player.sendMessage(StringUtil.CC("&aYou successfully reloaded the config and language files."));
        }
        else
            player.sendMessage(LanguageManager.getString("no-permission"));
    }

}
