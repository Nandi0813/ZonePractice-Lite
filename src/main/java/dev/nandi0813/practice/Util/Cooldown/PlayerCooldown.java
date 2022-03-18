package dev.nandi0813.practice.Util.Cooldown;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerCooldown
{

    @Getter private static final HashMap<Player, HashMap<CooldownObject, Long>> cooldowns = new HashMap<>();

    public static void addCooldown(Player player, CooldownObject object, int time)
    {
        if (!getCooldowns().containsKey(player))
        {
            HashMap<CooldownObject, Long> cooldowns = new HashMap<>();
            getCooldowns().put(player, cooldowns);
        }
        HashMap<CooldownObject, Long> cooldowns = getCooldowns().get(player);
        cooldowns.put(object, System.currentTimeMillis() + time * 1000L);
        getCooldowns().put(player, cooldowns);
    }

    public static void removeCooldown(Player player, CooldownObject object)
    {
        if (cooldowns.containsKey(player))
            cooldowns.get(player).remove(object);
    }

    public static boolean isActive(Player player, CooldownObject object)
    {
        if (cooldowns.containsKey(player))
            return cooldowns.get(player).containsKey(object) && System.currentTimeMillis() < cooldowns.get(player).get(object);
        return false;
    }

    public static long getLeft(Player player, CooldownObject object)
    {
        if (isActive(player, object))
            return Math.max(cooldowns.get(player).get(object) - System.currentTimeMillis(), 0L);
        return 0L;
    }

}
