package dev.nandi0813.practice;

import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.AdMessageUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Practice extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        AdMessageUtil.sendAdMessagesToConsole();

        SystemManager.Enable(this);

        // BStats
        Metrics metrics = new Metrics(this, 16054);
    }

    @Override
    public void onDisable()
    {
        SystemManager.Disable();
    }

    public static Practice getInstance()
    {
        return Practice.getPlugin(Practice.class);
    }

}
