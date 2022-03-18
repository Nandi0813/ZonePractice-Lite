package dev.nandi0813.practice.Util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InventoryUtil
{

    public static Inventory createInventory(String title, int row)
    {
        return Bukkit.getServer().createInventory(null, row * 9, StringUtil.CC(title));
    }



}
