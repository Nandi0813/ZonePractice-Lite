package dev.nandi0813.practice.Manager.Ladder.Custom.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Ladder.Custom.CustomLadderManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class KitSumGui
{

    public static void openGui(Player player, Ladder ladder)
    {
        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.summary.title").replaceAll("%ladderName%", ladder.getName()), 1);
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        player.openInventory(gui);

        // Set kit columns
        if (profile.getCustomKits().get(ladder) != null)
        {
            gui.setItem(0, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.existing-kit-item-name"),Material.GOLD_SWORD));

            gui.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));

            gui.setItem(2, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.edit-kit-item-name"), Material.BOOK));
            gui.setItem(3, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.delete-kit-item-name"), Material.REDSTONE, getDeleteItemLore()));
        }
        else
        {
            gui.setItem(0, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.create-kit-item-name"),Material.STONE_SWORD));
        }

        gui.setItem(8, ItemUtil.createItem("&cBack to kit selector", Material.ARROW));

        // Set filler items between kits
        for (int i = 0; i < gui.getSize(); i++)
        {
            if (gui.getItem(i) == null)
                gui.setItem(i, CustomLadderManager.getFillerItem());
        }

        // Set players status details
        CustomLadderManager.getOpenedEditor().put(player, ladder);
        profile.setStatus(ProfileStatus.EDITOR);
    }

    public static ArrayList<String> getDeleteItemLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&c&lYou won't be able to");
        lore.add("&crecover this kit.");
        return lore;
    }

}
