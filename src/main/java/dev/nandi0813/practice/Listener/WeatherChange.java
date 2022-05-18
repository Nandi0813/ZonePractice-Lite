package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener
{

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if (ConfigManager.getBoolean("world-settings.cancel-weather"))
        {
            boolean rain = e.toWeatherState();
            if (rain) e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onThunderChange(ThunderChangeEvent e)
    {
        if (ConfigManager.getBoolean("world-settings.cancel-weather"))
        {
            boolean storm = e.toThunderState();
            if (storm)
                e.setCancelled(true);
        }
    }

}
