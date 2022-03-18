package dev.nandi0813.practice.Util;

import org.bukkit.inventory.ItemStack;

public class ArmorUtil
{

    public static boolean isHelmet(ItemStack item)
    {
        return item.getType().name().endsWith("_HELMET");
    }

    public static boolean isChestplate(ItemStack item)
    {
        return item.getType().name().endsWith("_CHESTPLATE") || item.getType().name().endsWith("ELYTRA");
    }

    public static boolean isLeggings(ItemStack item)
    {
        return item.getType().name().endsWith("_LEGGINGS");
    }

    public static boolean isBoots(ItemStack item)
    {
        return item.getType().name().endsWith("_BOOTS");
    }

}
