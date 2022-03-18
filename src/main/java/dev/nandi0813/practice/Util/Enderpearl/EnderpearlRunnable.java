package dev.nandi0813.practice.Util.Enderpearl;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import dev.nandi0813.practice.Util.Cooldown.PlayerCooldown;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderpearlRunnable extends BukkitRunnable
{

    @Getter private final Player player;
    @Getter private final Profile profile;
    @Getter private boolean running;
    @Getter private final int seconds;

    public EnderpearlRunnable(Player player)
    {
        this.player = player;
        profile = SystemManager.getProfileManager().getProfiles().get(player);
        seconds = ConfigManager.getInt("match-settings.enderpearl.cooldown");
    }

    public void begin()
    {
        running = true;
        PlayerCooldown.addCooldown(player, CooldownObject.ENDER_PEARL, seconds);
        this.runTaskTimerAsynchronously(Practice.getInstance(), 0, 20L);
    }

    @Override
    public void cancel()
    {
        if (running)
        {
            Bukkit.getScheduler().cancelTask(this.getTaskId());
            running = false;
            PlayerCooldown.removeCooldown(player, CooldownObject.ENDER_PEARL);
        }
    }

    @Override
    public void run()
    {
        if (!PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL) && !profile.getStatus().equals(ProfileStatus.MATCH))
        {
            cancel();
        }
    }
}
