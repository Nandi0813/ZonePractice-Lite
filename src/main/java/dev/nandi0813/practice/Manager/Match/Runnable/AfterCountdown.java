package dev.nandi0813.practice.Manager.Match.Runnable;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AfterCountdown extends BukkitRunnable
{

    private final Match match;
    @Getter private int seconds;
    @Getter private boolean running = false;

    public AfterCountdown(Match match)
    {
        this.match = match;
        seconds = ConfigManager.getConfig().getInt("match-settings.after-countdown");
    }

    public void begin()
    {
        running = true;
        match.setStatus(MatchStatus.END);

        match.getStartCountdown().cancel();
        match.getDurationCountdown().cancel();

        this.runTaskTimer(Practice.getInstance(), 0, 20L);
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
        if (seconds == 0)
        {
            this.cancel();
            match.setAfterCountdown(new AfterCountdown(match));

            match.resetMap();
            match.endMatch();
        }

        seconds--;
    }

}
