package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnableArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setenable <ladder_id>/<ladder_name>"));
            return;
        }

        Ladder ladder;
        if (StringUtil.isInteger(args[1]))
            ladder = Practice.getLadderManager().getLadder(Integer.parseInt(args[1]));
        else
            ladder = Practice.getLadderManager().getLadder(args[1]);

        if (ladder == null)
        {
            player.sendMessage(StringUtil.CC("&cInvalid ladder id or name."));
            return;
        }

        if (ladder.isEnabled())
        {
            if (!Practice.getMatchManager().getLiveMatchByLadder(ladder).isEmpty())
            {
                player.sendMessage(StringUtil.CC("&cYou cannot disable a ladder that player's play in live matches."));
                return;
            }

            ladder.setEnabled(false);

            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
            {
                for (Profile profile : Practice.getProfileManager().getProfiles().values())
                {
                    profile.getCustomKits().remove(ladder);
                    profile.getFile().deleteCustomKit(ladder);
                }
            });

            player.sendMessage(StringUtil.CC("&eYou successfully &cdisabled &ethe " + ladder.getName() + " &aladder."));
        }
        else
        {
            if (!ladder.isReadyToEnable())
            {
                player.sendMessage(StringUtil.CC("&cLadder does not meet all the requirements. Please at least set a name, inventory and an icon for the ladder to meet these requirements."));
                return;
            }

            ladder.setEnabled(true);

            final int defaultElo = ConfigManager.getInt("ranked.default-elo");
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
            {
                for (Profile profile : Practice.getProfileManager().getProfiles().values())
                {
                    if (!profile.getLadderUnRankedWins().containsKey(ladder))
                        profile.getLadderUnRankedWins().put(ladder, 0);
                    if (!profile.getLadderUnRankedLosses().containsKey(ladder))
                        profile.getLadderUnRankedLosses().put(ladder, 0);

                    // Set the default stats for the ladder
                    if (ladder.isRanked())
                    {
                        if (!profile.getElo().containsKey(ladder))
                            profile.getElo().put(ladder, defaultElo);

                        if (!profile.getLadderRankedWins().containsKey(ladder))
                            profile.getLadderRankedWins().put(ladder, 0);
                        if (!profile.getLadderRankedLosses().containsKey(ladder))
                            profile.getLadderRankedLosses().put(ladder, 0);
                    }
                }
            });

            player.sendMessage(StringUtil.CC("&eYou successfully &aenabled &ethe " + ladder.getName() + " &aladder."));
        }

        Practice.getGuiManager().searchGUI(GUIType.QUEUE_UNRANKED).update();
        Practice.getGuiManager().searchGUI(GUIType.QUEUE_RANKED).update();
    }

}
