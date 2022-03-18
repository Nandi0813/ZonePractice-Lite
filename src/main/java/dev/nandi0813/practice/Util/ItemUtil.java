package dev.nandi0813.practice.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtil
{

    public static ItemStack createItemWithAmount(String displayname, Material material, int amount, List<String> lore)
    {
        ItemStack itemstack = new ItemStack(material, amount);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        itemmeta.setLore(StringUtil.CC(lore));
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(String displayname, Material material)
    {
        ItemStack itemstack = new ItemStack(material);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(Material material, Short damage)
    {
        ItemStack itemstack = new ItemStack(material, 1, damage);
        ItemMeta itemmeta = itemstack.getItemMeta();
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(String displayname, Material material, Short damage)
    {
        ItemStack itemstack = new ItemStack(material, 1, damage);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(String displayname, Material material, List<String> lore)
    {
        ItemStack itemstack = new ItemStack(material);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        itemmeta.setLore(StringUtil.CC(lore));
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(String displayname, Material material, List<String> lore, boolean enchantEffect)
    {
        ItemStack itemstack = new ItemStack(material);
        ItemMeta itemmeta = itemstack.getItemMeta();
        if (enchantEffect) itemmeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        itemmeta.setLore(StringUtil.CC(lore));
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(String displayname, Material material, Short damage, List<String> lore)
    {
        ItemStack itemstack = new ItemStack(material, 1, damage);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(StringUtil.CC(displayname));
        itemmeta.setLore(StringUtil.CC(lore));
        hideItemFlags(itemmeta);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public static ItemStack createItem(ItemStack item, List<String> lore)
    {
        ItemStack itemstack = new ItemStack(item.getType());
        itemstack.setDurability(item.getDurability());
        if (item.hasItemMeta()) {
            ItemMeta itemmeta = itemstack.getItemMeta();
            itemmeta.setDisplayName(StringUtil.CC(item.getItemMeta().getDisplayName()));
            itemmeta.setLore(StringUtil.CC(lore));
            hideItemFlags(itemmeta);
            itemstack.setItemMeta(itemmeta);
        }
        return itemstack;
    }


    public static void hideItemFlags(ItemMeta itemMeta)
    {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    public static ItemStack hideItemFlags(ItemStack item)
    {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        return item;
    }

}
