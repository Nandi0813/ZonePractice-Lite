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

public class Axe extends Ladder
{

    public Axe(String name)
    {
        super(name);
        setEnabled(true);
        setIcon(ItemUtil.createItem("&7&lAxe", Material.IRON_AXE, Short.valueOf("0")));
        setRanked(true);
        setArmor(armor());
        setInventory(inventory());
        setEffects(new ArrayList<>(Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 10000, 1))));
        setKnockbackType(KnockbackType.DEFAULT);
        setHitDelay(20);
        setEditable(true);
        setRegen(true);
        setHunger(true);
        setBuild(false);
    }

    public ItemStack[] armor()
    {
        return new ItemStack[] {
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    public ItemStack[] inventory()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_AXE).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack(),
                new ItemStack(Material.GOLDEN_APPLE, 16),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
        };
    }

}
