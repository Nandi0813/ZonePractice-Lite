package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

public class PlayerUtil
{

    public static void setPlayerData(Player player, boolean fly, boolean entityCollide)
    {
        player.closeInventory();
        player.setFallDistance(0);
        player.setHealth(20);
        player.setExp(0);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setNoDamageTicks(20);
        player.setFireTicks(0);
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.setAllowFlight(fly);
        player.setFlying(fly);
        player.spigot().setCollidesWithEntities(entityCollide);
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static ItemStack getPlayerHead(Player player)
    {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

    public static void setupPlayerRankedPerDay(Player player)
    {
        if (!SystemManager.getMatchManager().getRankedPerDay().containsKey(player))
            SystemManager.getMatchManager().getRankedPerDay().put(player, 0);

        for (String permissionName : ConfigManager.getConfigSectionKeys("ranked.ranked-per-day"))
        {
            String permission = ConfigManager.getString("ranked.ranked-per-day." + permissionName + ".permission");

            if (permission != null)
            {
                if (player.isOp())
                {
                    SystemManager.getMatchManager().getAllowedRankedPerDay().put(player, 1000000000);
                    return;
                }
                else if (player.hasPermission(permission))
                {
                    int allowed_rankeds = ConfigManager.getInt("ranked.ranked-per-day." + permissionName + ".allowed-rankeds");
                    SystemManager.getMatchManager().getAllowedRankedPerDay().put(player, allowed_rankeds);

                    return;
                }
            }
        }

        SystemManager.getMatchManager().getAllowedRankedPerDay().put(player, ConfigManager.getInt("ranked.ranked-per-day.default"));
    }

}
