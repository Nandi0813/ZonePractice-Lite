package dev.nandi0813.practice.Manager.Ladder.Custom.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitSelectorGui
{

    @Getter public static Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.selector.title"), getRowSize());

    public static void updateGui()
    {
        gui.clear();
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isEditable())
            {
                ItemStack icon = ladder.getIcon().clone();
                ItemMeta iconMeta = icon.getItemMeta();
                ItemUtil.hideItemFlags(iconMeta);

                ArrayList<String> lore = new ArrayList<>();
                for (String line : LanguageManager.getList("gui.kit-editor.selector.item-lore"))
                    lore.add(line.replaceAll("%ladderName%", ladder.getName()));
                iconMeta.setLore(lore);

                icon.setItemMeta(iconMeta);
                gui.addItem(icon);
            }
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers())
        {
            if (online.getOpenInventory().getTitle().equals(getGui().getTitle()))
                online.updateInventory();
        }
    }

    public static int getRowSize()
    {
        List<Ladder> editableladders = new ArrayList<>();
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isEditable()) editableladders.add(ladder);
        }
        if (editableladders.size() > 9)
        {
            if (editableladders.size() % 9 == 0)
                return editableladders.size() / 9;
            else
                return editableladders.size() / 9 + 1;
        }
        return 1;
    }

}
