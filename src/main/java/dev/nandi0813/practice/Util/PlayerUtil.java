package dev.nandi0813.practice.Util;

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

}
