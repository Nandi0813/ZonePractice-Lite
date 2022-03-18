package dev.nandi0813.practice.Command.Practice.Arguments;

import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ArenasArg
{

    public static void ArenasCommand(Player player)
    {
        player.teleport(SystemManager.getArenaManager().getArenasWorld().getSpawnLocation());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}
