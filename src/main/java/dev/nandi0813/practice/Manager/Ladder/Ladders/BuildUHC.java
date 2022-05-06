package dev.nandi0813.practice.Manager.Ladder.Ladders;

import dev.nandi0813.practice.Manager.Ladder.KnockbackType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Util.ItemBuilder;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BuildUHC extends Ladder
{

    public BuildUHC(String name)
    {
        super(name);
        setEnabled(true);
        setIcon(ItemUtil.createItem("&6&lBuildUHC", Material.LAVA_BUCKET, Short.valueOf("0")));
        setRanked(true);
        setArmor(armor());
        setInventory(inventory());
        setEffects(new ArrayList<>());
        setKnockbackType(KnockbackType.DEFAULT);
        setHitDelay(20);
        setEditable(true);
        setRegen(true);
        setHunger(true);
        setBuild(true);
    }

    public ItemStack[] armor()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.PROTECTION_FALL, 4).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
        };
    }

    public ItemStack[] inventory()
    {
        return new ItemStack[] {
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.FISHING_ROD, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.BOW, 1, (short)0).addEnchant(Enchantment.ARROW_DAMAGE, 3).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.LAVA_BUCKET, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.WATER_BUCKET, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.GOLDEN_APPLE, 3, (short)0).setName("§6§lGolden Head").toItemStack(),
                new ItemBuilder(Material.GOLDEN_APPLE, 6, (short)0).toItemStack(),
                new ItemBuilder(Material.COOKED_BEEF, 64, (short)0).toItemStack(),
                new ItemBuilder(Material.COBBLESTONE, 64, (short)0).toItemStack(),
                new ItemBuilder(Material.ARROW, 32, (short)0).toItemStack(),
                new ItemBuilder(Material.DIAMOND_PICKAXE, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.DIAMOND_AXE, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.LAVA_BUCKET, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.WATER_BUCKET, 1, (short)0).toItemStack(),
                new ItemBuilder(Material.WOOD, 64, (short)0).toItemStack(),
        };
    }

}
