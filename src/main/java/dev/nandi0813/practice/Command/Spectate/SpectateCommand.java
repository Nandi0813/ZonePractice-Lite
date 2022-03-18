package dev.nandi0813.practice.Command.Spectate;

import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (!player.hasPermission("zonepractice.spectate"))
            {
                player.sendMessage(StringUtil.CC("&cYou don't have permission!"));
                return false;
            }

            if (args.length != 1)
                player.sendMessage(StringUtil.CC("&c/" + label + " <player>"));

            else
            {
                Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
                Player target = Bukkit.getPlayer(args[0]);
                Party party = SystemManager.getPartyManager().getParty(player);

                if ((profile.getStatus().equals(ProfileStatus.LOBBY) || profile.getStatus().equals(ProfileStatus.SPECTATE)) && party == null)
                {
                    if (target != null)
                    {
                        if (target != player)
                        {
                            Profile targetProfile = SystemManager.getProfileManager().getProfiles().get(target);
                            Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(target);

                            if (targetProfile.getStatus().equals(ProfileStatus.MATCH))
                            {
                                match.addSpectator(player);
                            }
                            else
                                player.sendMessage(StringUtil.CC("&cPlayer isn't in match."));
                        }
                        else
                            player.sendMessage(StringUtil.CC("&cYou can't spectate yourself."));
                    }
                    else
                        player.sendMessage(StringUtil.CC("&cPlayer isn't online."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cYou can't spectate anyone right now."));
            }

        }
        return true;
    }

}
