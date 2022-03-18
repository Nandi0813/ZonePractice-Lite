package dev.nandi0813.practice.Manager.Ladder;

import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Ladder
{

    @Getter @Setter private String name;
    @Getter @Setter private ItemStack icon;
    @Getter @Setter private ItemStack[] armor;
    @Getter @Setter private ItemStack[] inventory;
    @Getter @Setter private List<PotionEffect> effects;
    @Getter @Setter private KnockbackType knockbackType;

    @Getter @Setter private int hitDelay;
    @Getter @Setter private boolean ranked;
    @Getter @Setter private boolean editable;
    @Getter @Setter private boolean regen;
    @Getter @Setter private boolean hunger;
    @Getter @Setter private boolean build;

    public Ladder(String name, ItemStack icon, boolean ranked, ItemStack[] armor, ItemStack[] inventory, List<PotionEffect> effects, int hitdelay, boolean editable, boolean regen, boolean hunger, boolean build) {
        this.name = name;
        this.icon = icon;
        this.ranked = ranked;
        this.armor = armor;
        this.inventory = inventory;
        this.effects = effects;
        this.hitDelay = hitdelay;
        this.editable = editable;
        this.regen = regen;
        this.hunger = hunger;
        this.build = build;
    }

    public Ladder(String name)
    {
        this.name = name;
        this.icon = ItemUtil.createItem("&6" + this.name, Material.ANVIL);
        this.ranked = false;
        this.armor = new ItemStack[4];
        this.inventory = new ItemStack[36];
        this.effects = new ArrayList<>();
        this.hitDelay = 20;
        this.editable = false;
        this.regen = true;
        this.hunger = true;
        this.build = false;
    }

}
