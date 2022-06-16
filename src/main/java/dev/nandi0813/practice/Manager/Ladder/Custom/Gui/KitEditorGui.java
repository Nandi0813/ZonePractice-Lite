package dev.nandi0813.practice.Manager.Ladder.Custom.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Ladder.Custom.CustomLadderManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KitEditorGui
{

    @Getter private static final ItemStack fillerItem = ItemUtil.createItem(" ", Material.STAINED_GLASS_PANE, Short.valueOf("1"));
    @Getter private static final ItemStack dummyItem = ItemUtil.createItem("dummy", Material.GLOWSTONE_DUST);

    public static void openGui(Player player, Ladder ladder)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        player.openInventory(buildGui(ladder));
        buildPlayerInventory(profile, ladder);

        profile.setStatus(ProfileStatus.EDITOR);
        CustomLadderManager.getOpenedEditor().put(player, ladder);
        CustomLadderManager.getOpenedKit().put(player, ladder);
    }

    public static Inventory buildGui(Ladder ladder)
    {
        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.editor.title").replaceAll("%ladderName%", ladder.getName()), 1);

        gui.setItem(7, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.load-item.name"), Material.WOOL, Short.valueOf("4"), LanguageManager.getList("gui.kit-editor.editor.load-item.lore")));
        gui.setItem(8, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.save-item.name"), Material.WOOL, Short.valueOf("5"), LanguageManager.getList("gui.kit-editor.editor.save-item.lore")));

        // Frame
        for (int i : new int[]{4,6})
            gui.setItem(i, CustomLadderManager.getFillerItem());

        gui.setItem(5, getEffectItem(ladder));

        ArrayList<ItemStack> armorContent = new ArrayList<>(Arrays.asList(ladder.getArmor()));
        Collections.reverse(armorContent);
        for (int i : new int[]{0,1,2,3})
        {
            ItemStack armor = armorContent.get(i);
            if (armor != null)
            {
                ItemMeta armorMeta = armor.getItemMeta();
                armorMeta.setDisplayName(LanguageManager.getString("gui.kit-editor.editor.auto-equip-item-name"));
                armor.setItemMeta(armorMeta);

                gui.setItem(i, armor);
            }
            else
                gui.setItem(i, dummyItem);
        }

        for (int i = 0; i < gui.getSize(); i++)
            if (gui.getItem(i) == null) gui.setItem(i, fillerItem);

        gui.remove(dummyItem);
        return gui;
    }

    public static void buildPlayerInventory(Profile profile, Ladder ladder)
    {
        Player player = profile.getPlayer().getPlayer();
        player.getInventory().clear();

        ItemStack[] content = profile.getCustomKits().get(ladder);

        if (content != null)
            player.getInventory().setContents(content);
        else
            player.getInventory().setContents(ladder.getInventory());
    }

    private static ItemStack getEffectItem(Ladder ladder)
    {
        ArrayList<String> lore = new ArrayList<>();
        ArrayList<String> effectList = new ArrayList<>();

        if (ladder.getEffects().size() > 0)
            for (PotionEffect potionEffect : ladder.getEffects())
                effectList.add("&d" + getPotionEffectNormalName(potionEffect.getType().getName()) + " " + (potionEffect.getAmplifier()+1) + " &7for " + StringUtil.formatMillisecondsToMinutes((potionEffect.getDuration()/20)* 1000L));
        else effectList.add("&7This ladder has no effects.");

        for (String line : LanguageManager.getList("gui.kit-editor.editor.effect-item.lore"))
        {
            if (line.contains("%effects%"))
            {
                line.replaceAll("%effects%", "");
                lore.addAll(effectList);
            }
            else
                lore.add(line);
        }

        return ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.effect-item.name"), Material.POTION, Short.valueOf("8233"), lore);
    }

    public static String getPotionEffectNormalName(String base)
    {
        return WordUtils.capitalize(base.replaceAll("_", "").toLowerCase());
    }

}
