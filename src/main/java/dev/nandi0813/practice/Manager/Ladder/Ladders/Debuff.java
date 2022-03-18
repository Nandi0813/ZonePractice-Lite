package dev.nandi0813.practice.Manager.Ladder.Ladders;

import dev.nandi0813.practice.Manager.Ladder.KnockbackType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Util.ItemBuilder;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Debuff extends Ladder
{

    public Debuff(String name) {
        super(name);
        setIcon(ItemUtil.createItem("&a&lDebuff", Material.POTION, Short.valueOf("16452")));
        setRanked(true);
        setArmor(armor());
        setInventory(inventory());
        setEffects(new ArrayList<>());
        setKnockbackType(KnockbackType.DEFAULT);
        setHitDelay(20);
        setEditable(true);
        setRegen(true);
        setHunger(true);
        setBuild(false);
    }

    public ItemStack[] armor() {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.PROTECTION_FALL, 4).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack()
        };
    }

    public ItemStack[] inventory()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.FIRE_ASPECT, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.ENDER_PEARL, 16, (short)0).toItemStack(),
                new ItemBuilder(Material.GOLDEN_CARROT, 64, (short)0).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8259).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16388).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16426).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16426).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16388).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16388).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16426).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),
        };
    }

}
