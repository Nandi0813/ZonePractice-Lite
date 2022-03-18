package dev.nandi0813.practice.Manager.Ladder.Ladders;

import dev.nandi0813.practice.Manager.Ladder.KnockbackType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Util.ItemBuilder;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

public class Combo extends Ladder
{

    public Combo(String name)
    {
        super(name);
        setIcon(ItemUtil.createItem("&a&lCombo", Material.RAW_FISH, Short.valueOf("3")));
        setRanked(true);
        setArmor(armor());
        setInventory(inventory());
        setEffects(new ArrayList<>(Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 10000, 1))));
        setKnockbackType(KnockbackType.COMBO);
        setHitDelay(4);
        setEditable(true);
        setRegen(true);
        setHunger(true);
        setBuild(false);
    }

    public ItemStack[] armor()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 2).addEnchant(Enchantment.PROTECTION_FALL, 4).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
        };
    }

    public ItemStack[] inventory()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).addEnchant(Enchantment.DURABILITY, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).toItemStack(),
                new ItemBuilder(Material.GOLDEN_APPLE, 64, (short)1).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 2).toItemStack(),
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 2).addEnchant(Enchantment.PROTECTION_FALL, 4).toItemStack(),
        };
    }

}
