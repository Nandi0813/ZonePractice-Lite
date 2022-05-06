package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EloresetArg
{

    public static void EloResetCommand(Player player, String label, String[] args)
    {
        if (args.length == 3)
        {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);

            if (targetProfile != null)
            {
                Ladder ladder = SystemManager.getLadderManager().getLadder(args[2]);

                if (ladder != null)
                {
                    targetProfile.getElo().put(ladder, ConfigManager.getInt("ranked.default-elo"));

                    player.sendMessage(StringUtil.CC("&aYou successfully reseted " + targetProfile.getPlayer().getName() + "'s elo in " + ladder.getName() + " ladder."));
                }
                else if (args[2].equalsIgnoreCase("all"))
                {
                    targetProfile.setElo(new HashMap<>());
                    for (Ladder ladder1 : SystemManager.getLadderManager().getLadders())
                        if (ladder1.isRanked())
                        {
                            targetProfile.getElo().put(ladder1, ConfigManager.getInt("ranked.default-elo"));
                        }

                    player.sendMessage(StringUtil.CC("&aYou successfully reseted all " + targetProfile.getPlayer().getName() + "'s elo stats."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou must give a valid ladder name or the word all."));
            }
            else
                player.sendMessage(StringUtil.CC("&cPlayer's profile cannot be found."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " eloreset <player> <ladder/all>"));
    }

}
