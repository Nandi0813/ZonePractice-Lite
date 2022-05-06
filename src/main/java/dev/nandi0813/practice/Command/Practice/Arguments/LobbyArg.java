package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LobbyArg
{

    public static void LobbyCommand(Player player, String label, String[] args)
    {
        if (args.length == 1)
        {
            if (ServerManager.getLobby() != null)
            {
                Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

                if (profile.getStatus().equals(ProfileStatus.MATCH) || profile.getStatus().equals(ProfileStatus.SPECTATE))
                {
                    player.sendMessage(StringUtil.CC("&cYou can't use this, while you are in a match or spectating one."));
                    return;
                }

                Location lobbyLocation = ServerManager.getLobby();
                player.teleport(lobbyLocation);
            }
            else
            {
                player.sendMessage(StringUtil.CC("&cLobby location is not set yet. You can set it by using the &l/practice lobby set &ccommand."));
            }
        }
        else if (args.length == 2 && args[1].equalsIgnoreCase("set"))
        {
            Location lobbyLocation = player.getLocation();

            if (!lobbyLocation.getWorld().equals(SystemManager.getArenaManager().getArenasWorld()))
            {
                ServerManager.setLobby(lobbyLocation);
                player.sendMessage(StringUtil.CC("&aYou successfully set the &elobby spawn&a."));
            }
            else
            {
                player.sendMessage(StringUtil.CC("&cYou can't place the lobby location in the arena worlds."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " lobby"));
            player.sendMessage(StringUtil.CC("&c/" + label + " lobby set"));
        }
    }

}
