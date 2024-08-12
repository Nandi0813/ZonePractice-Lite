package dev.nandi0813.practice.Manager.Ladder;

import dev.nandi0813.practice.Manager.File.LadderFile;
import dev.nandi0813.practice.Util.ItemSerializationUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Ladder
{

    private final int id;
    private String name = null;
    private boolean enabled = false;
    private ItemStack icon = null;

    private ItemStack[] armor = null;
    private ItemStack[] inventory = null;
    private List<PotionEffect> effects = new ArrayList<>();

    private KnockbackType knockbackType = KnockbackType.DEFAULT;
    private int hitDelay = 20;
    private boolean ranked = false;
    private boolean editable = true;
    private boolean regen = true;
    private boolean invulnerability = false;
    private boolean deadInWater = false;
    private boolean hunger = true;
    private boolean build = false;

    public Ladder(int id) {
        this.id = id;
        getData();
    }

    public void getData()
    {
        FileConfiguration config = LadderFile.getConfig();
        String path = "ladders.ladder" + id;

        if (!config.isConfigurationSection(path))
            return;

        String namePath = path + ".name";
        if (config.isSet(namePath) && config.isString(namePath))
            name = config.getString(namePath);

        String enabledPath = path + ".enabled";
        if (config.isSet(enabledPath) && config.isBoolean(enabledPath))
            enabled = config.getBoolean(enabledPath);

        String invulnerabilityPath = path + ".invulnerability";
        if (config.isSet(invulnerabilityPath) && config.isBoolean(invulnerabilityPath))
            invulnerability = config.getBoolean(invulnerabilityPath);

        String deadInWaterPath = path + ".dead-in-water";
        if (config.isSet(deadInWaterPath) && config.isBoolean(deadInWaterPath))
            deadInWater = config.getBoolean(deadInWaterPath);

        String iconPath = path + ".icon";
        if (config.isSet(iconPath) && config.isItemStack(iconPath))
            icon = config.getItemStack(iconPath);

        String armorPath = path + ".armor";
        if (config.isSet(armorPath) && config.isString(armorPath))
        {
            try {
                armor = ItemSerializationUtil.itemStackArrayFromBase64(config.getString(armorPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String inventoryPath = path + ".inventory";
        if (config.isSet(inventoryPath) && config.isString(inventoryPath))
        {
            try {
                inventory = ItemSerializationUtil.itemStackArrayFromBase64(config.getString(inventoryPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String effectPath = path + ".effects";
        if (config.isSet(effectPath) && config.isList(effectPath))
            effects = (List<PotionEffect>) config.getList(effectPath);

        String knockbackPath = path + ".knockback";
        if (config.isSet(knockbackPath) && config.isString(knockbackPath))
            knockbackType = KnockbackType.valueOf(config.getString(knockbackPath));

        String hitdelayPath = path + ".hitdelay";
        if (config.isSet(hitdelayPath) && config.isInt(hitdelayPath))
            hitDelay = config.getInt(hitdelayPath);

        String rankedPath = path + ".ranked";
        if (config.isSet(rankedPath) && config.isBoolean(rankedPath))
            ranked = config.getBoolean(rankedPath);

        String editablePath = path + ".editable";
        if (config.isSet(editablePath) && config.isBoolean(editablePath))
            editable = config.getBoolean(editablePath);

        String regenPath = path + ".regen";
        if (config.isSet(regenPath) && config.isBoolean(regenPath))
            regen = config.getBoolean(regenPath);

        String hungerPath = path + ".hunger";
        if (config.isSet(hungerPath) && config.isBoolean(hungerPath))
            hunger = config.getBoolean(hungerPath);

        String buildPath = path + ".build";
        if (config.isSet(buildPath) && config.isBoolean(buildPath))
            build = config.getBoolean(buildPath);

        if (enabled && !isReadyToEnable())
            enabled = false;
    }

    public boolean isReadyToEnable()
    {
        return name != null && icon != null && armor != null && inventory != null && knockbackType != null;
    }

    public void saveData(boolean saveFile)
    {
        FileConfiguration config = LadderFile.getConfig();
        String path = "ladders.ladder" + id;

        if (name != null)
        {
            String namePath = path + ".name";
            config.set(namePath, name);
        }

        String enabledPath = path + ".enabled";
        config.set(enabledPath, enabled);

        if (icon != null)
        {
            String iconPath = path + ".icon";
            config.set(iconPath, icon);
        }

        if (armor != null)
        {
            String armorPath = path + ".armor";
            config.set(armorPath, ItemSerializationUtil.itemStackArrayToBase64(armor));
        }

        if (inventory != null)
        {
            String inventoryPath = path + ".inventory";
            config.set(inventoryPath, ItemSerializationUtil.itemStackArrayToBase64(inventory));
        }

        String effectPath = path + ".effects";
        if (effects != null && !effects.isEmpty())
        {
            config.set(effectPath, effects);
        }
        else
        {
            config.set(effectPath, null);
        }

        if (knockbackType != null)
        {
            String knockbackPath = path + ".knockback";
            config.set(knockbackPath, knockbackType.name().toUpperCase());
        }

        String hitdelayPath = path + ".hitdelay";
        config.set(hitdelayPath, hitDelay);

        String rankedPath = path + ".ranked";
        config.set(rankedPath, ranked);

        String editablePath = path + ".editable";
        config.set(editablePath, editable);

        String regenPath = path + ".regen";
        config.set(regenPath, regen);

        String invulnerabilityPath = path + ".invulnerability";
        config.set(invulnerabilityPath, invulnerability);

        String deadInWaterPath = path + ".dead-in-water";
        config.set(deadInWaterPath, deadInWater);

        String hungerPath = path + ".hunger";
        config.set(hungerPath, hunger);

        String buildPath = path + ".build";
        config.set(buildPath, build);

        if (saveFile)
            LadderFile.save();
    }

}
