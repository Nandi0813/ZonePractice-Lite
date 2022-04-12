package dev.nandi0813.practice.Manager.Queue;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class QueueRunnable extends BukkitRunnable
{

    @Getter private final Queue queue;
    @Getter private int seconds;
    @Getter private boolean running = false;

    public QueueRunnable(Queue queue)
    {
        this.queue = queue;
    }

    public void begin()
    {
        running = true;
        this.runTaskTimerAsynchronously(Practice.getInstance(), 0L, 20L);
    }

    @Override
    public void cancel()
    {
        if (running)
        {
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            running = false;
        }
    }

    @Override
    public void run()
    {
        if (seconds == 300)
        {
            Bukkit.getScheduler().runTask(Practice.getInstance(), () ->
            {
                queue.endQueue(false);
                queue.getPlayer().sendMessage(LanguageManager.getString("queue.no-match"));
            });
        }

        seconds++;
    }
}
