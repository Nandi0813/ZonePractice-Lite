package dev.nandi0813.practice.Util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryUtil {

    public static Inventory createInventory(String title, int row)
    {
        return Bukkit.getServer().createInventory(null, row * 9, StringUtil.CC(title));
    }

    /*
    public static ItemStack[] reverseInventoryContent(ItemStack[] baseContent)
    {
        ArrayList<ItemStack> content = new ArrayList<>(Arrays.asList(baseContent));
        ArrayList<ItemStack> newContent = new ArrayList<>();

        ArrayList<ItemStack> newArray1 = new ArrayList<>();
        ArrayList<ItemStack> newArray2 = new ArrayList<>();
        ArrayList<ItemStack> newArray3 = new ArrayList<>();
        ArrayList<ItemStack> newArray4 = new ArrayList<>();

        for (int i = 0; i < 9; i++) newArray1.add(content.get(i));
        for (int i = 9; i < 18; i++) newArray2.add(content.get(i));
        for (int i = 18; i < 27; i++) newArray3.add(content.get(i));
        for (int i = 27; i < 36; i++) newArray4.add(content.get(i));

        newContent.addAll(newArray2);
        newContent.addAll(newArray3);
        newContent.addAll(newArray4);
        newContent.addAll(newArray1);

        return newContent.toArray(new ItemStack[0]);
    }
     */

}