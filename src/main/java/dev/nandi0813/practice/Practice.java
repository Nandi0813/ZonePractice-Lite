package dev.nandi0813.practice;

import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Practice extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        SystemManager.Enable(this);
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
