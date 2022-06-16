package dev.nandi0813.practice.Command.Matchinv;

import dev.nandi0813.practice.Manager.Gui.Match.MatchStatsGui;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MatchinvCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (!profile.getStatus().equals(ProfileStatus.OFFLINE))
            {
                if (args.length != 2)
                    player.sendMessage(StringUtil.CC("&c/" + label + " <matchid> <player>"));
                else
                {
                    Match match = SystemManager.getMatchManager().getMatches().get(args[0]);
                    Player target = Bukkit.getPlayer(args[1]);

                    if (match != null)
                    {
                        if (target != null && match.getPlayers().contains(target) && match.getMatchStats().containsKey(target) && match.getMatchStats().get(target).isSet())
                        {
                            player.openInventory(MatchStatsGui.createMatchStatsGui(match.getMatchStats().get(target)));
                        }
                        else
                            player.sendMessage(StringUtil.CC("&cPlayer cannot be found."));
                    }
                    else
                        player.sendMessage(StringUtil.CC("&cMatch cannot be found."));
                }
            }
        }
        return true;
    }

}
