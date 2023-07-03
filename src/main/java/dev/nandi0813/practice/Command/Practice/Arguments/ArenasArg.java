package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ArenasArg
{

    public static void ArenasCommand(Player player)
    {
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH) || profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            player.sendMessage(StringUtil.CC("&cYou can't use this, while you are in a match or spectating one."));
            return;
        }

        player.teleport(Practice.getArenaManager().getArenasWorld().getSpawnLocation());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}
