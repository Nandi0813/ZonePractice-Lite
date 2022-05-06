package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndmatchArg
{

    public static void MatchForceendCommand(Player player, String label, String[] args)
    {
        if (player.hasPermission("zonepractice.match.forceend"))
        {
            if (args.length == 2)
            {
                Player target = Bukkit.getPlayer(args[1]);

                if (target != null)
                {
                    Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(target);

                    if (match != null)
                    {
                        if (!match.getStatus().equals(MatchStatus.END))
                        {
                            for (Player matchPlayer : match.getPlayers())
                            {
                                if (matchPlayer.hasPermission("zonepractice.bypass.match.forceend"))
                                {
                                    player.sendMessage(StringUtil.CC("&cYou can't end this match."));
                                    return;
                                }
                            }

                            match.sendMessage(LanguageManager.getString("match.force-end"), true);
                            player.sendMessage(StringUtil.CC("&aYou successfully ended " + target.getName() + "'s match."));

                            match.endMatch();
                        }
                        else
                            player.sendMessage(StringUtil.CC("&cThe match is already ended."));
                    }
                    else
                        player.sendMessage(StringUtil.CC("&cPlayer is not in a match."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cPlayer is not online."));
            }
            else
                player.sendMessage(StringUtil.CC("&c/" + label + " endmatch <player>"));
        }
        else
            player.sendMessage(LanguageManager.getString("no-permission"));
    }

}
