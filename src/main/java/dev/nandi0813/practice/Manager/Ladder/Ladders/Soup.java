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

public class Soup extends Ladder
{

    public Soup(String name)
    {
        super(name);
        setEnabled(true);
        setIcon(ItemUtil.createItem("&6&lSoup", Material.MUSHROOM_SOUP, Short.valueOf("0")));
        setRanked(true);
        setArmor(armor());
        setInventory(inventory());
        setEffects(new ArrayList<>(Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 6000, 1))));
        setKnockbackType(KnockbackType.DEFAULT);
        setHitDelay(20);
        setEditable(true);
        setRegen(true);
        setHunger(false);
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
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 0).toItemStack(),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),

                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),

                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),

                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
                new ItemStack(Material.MUSHROOM_SOUP),
        };
    }

}
