package dev.nandi0813.practice.Command;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.StatsGui;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (!player.hasPermission("zonepractice.stats"))
            {
                player.sendMessage(LanguageManager.getString("no-permission"));
                return false;
            }

            if (!profile.getStatus().equals(ProfileStatus.MATCH))
            {
                if (args.length == 0)
                {
                    player.openInventory(StatsGui.getStatsGui(profile));
                }
                else if (args.length == 1)
                {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);

                    if (targetProfile != null)
                    {
                        player.openInventory(StatsGui.getStatsGui(targetProfile));
                    }
                    else
                        player.sendMessage(LanguageManager.getString("stats.player-not-found"));
                }
                else
                    player.sendMessage(StringUtil.CC("&c/" + label + " <player>"));
            }
            else
                player.sendMessage(LanguageManager.getString("stats.cant-use"));

        }
        return true;
    }

}
