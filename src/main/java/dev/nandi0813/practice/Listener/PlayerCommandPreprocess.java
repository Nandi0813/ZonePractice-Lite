package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener
{

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onReloadCommand(PlayerCommandPreprocessEvent e)
    {
        String cmd = e.getMessage().split(" ")[0].replace("/", "").replaceAll("(?i)bukkit:", "");

        if (cmd.equalsIgnoreCase("reload") || cmd.equalsIgnoreCase("rl"))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage(StringUtil.CC("&cReload is disabled. Please completely restart the server."));
        }
    }

}
