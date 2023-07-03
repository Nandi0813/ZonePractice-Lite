package dev.nandi0813.practice.Manager.Queue;

import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QueueListener implements Listener
{

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Queue queue = Practice.getQueueManager().getQueue(player);

        if (queue != null)
            queue.endQueue(false);
    }

}
