package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MultiGameListener implements Listener
{

    @EventHandler (priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        World from = e.getFrom().getWorld();
        World to = e.getTo().getWorld();

        if (ServerManager.getLobby() != null)
        {
            World lobby = ServerManager.getLobby().getWorld();
            World arenas = Practice.getArenaManager().getArenasWorld();

            if (ConfigManager.getBoolean("multi-game-support"))
            {
                if ((!from.equals(arenas) && !from.equals(lobby)) && to.equals(lobby))
                {
                    Bukkit.getServer().getScheduler().runTaskLater(Practice.getInstance(), () ->
                    {
                        Practice.getInventoryManager().getSpawnInventory().setInventory(player, true);
                        Practice.getSidebarManager().loadSidebar(player);
                    }, 2L);
                }
                else if (from.equals(lobby) && (!to.equals(arenas) && !to.equals(lobby)))
                {
                    player.getInventory().clear();

                    Party party = Practice.getPartyManager().getParty(player);
                    if (party != null)
                        party.removeMember(player, false);

                    profile.setStatus(ProfileStatus.OFFLINE);
                    profile.saveData();

                    Practice.getSidebarManager().unLoadSidebar(player);
                }
            }
        }
        else if (player.isOp())
            player.sendMessage(StringUtil.CC("&cPractice lobby location is not set yet!"));
    }

}
