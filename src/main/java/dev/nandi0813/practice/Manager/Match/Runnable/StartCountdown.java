package dev.nandi0813.practice.Manager.Match.Runnable;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class StartCountdown extends BukkitRunnable
{

    private final Match match;
    private int seconds;
    @Getter private boolean running = false;

    public StartCountdown(Match match)
    {
        this.match = match;
        seconds = ConfigManager.getConfig().getInt("match-settings.start-countdown");
    }

    public void begin()
    {
        running = true;
        this.runTaskTimer(Practice.getInstance(), 20L, 20L);
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
            match.setStartCountdown(new StartCountdown(match));

            match.setDurationCountdown(new DurationCountdown());
            match.getDurationCountdown().begin();

            match.setStatus(MatchStatus.LIVE);
            match.sendMessage("&aMatch has been started.", true);

            for (Player player : match.getPlayers())
                for (PotionEffect potionEffect : match.getLadder().getEffects())
                    player.addPotionEffect(potionEffect);
        }
        else
        {
            match.sendMessage("&eMatch is starting in &6" + seconds + " &eseconds.", false);
        }

        seconds--;
    }
}
