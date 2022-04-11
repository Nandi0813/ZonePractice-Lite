package dev.nandi0813.practice.Manager.Gui.Match;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.UnrankedGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LadderSelector
{

    public static Inventory getGui(MatchType matchType)
    {
        String title = LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", matchType.getName());
        Inventory gui = InventoryUtil.createInventory(title, UnrankedGui.getRowSize());

        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            ItemStack icon = ladder.getIcon().clone();
            ItemMeta iconMeta = icon.getItemMeta();
            ItemUtil.hideItemFlags(iconMeta);

            List<String> lore = new ArrayList<>();
            for (String line : LanguageManager.getList("gui.ladder-selector.item-lore"))
            {
                lore.add(line
                        .replaceAll("%ladderName%", ladder.getName())
                        .replaceAll("%matchTypeName%", matchType.getName()));
            }
            iconMeta.setLore(lore);

            icon.setItemMeta(iconMeta);
            gui.addItem(icon);
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers())
            if (online.getOpenInventory().getTitle().equals(title))
                online.updateInventory();

        return gui;
    }

}
