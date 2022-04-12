package dev.nandi0813.practice.Manager.Match.Runnable;

import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DurationCountdown extends BukkitRunnable
{

    @Getter private int seconds;
    @Getter private boolean running = false;

    public DurationCountdown()
    {
        seconds = 0;
    }

    public void begin()
    {
        running = true;
        this.runTaskTimerAsynchronously(Practice.getInstance(), 0, 20L);
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
        seconds++;
    }

}
