package dev.nandi0813.practice.Manager.Gui.Match;

import dev.nandi0813.practice.Manager.Gui.UnrankedGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LadderSelector
{

    public static Inventory getGui(MatchType matchType)
    {
        String title = matchType.getName() + "&8 - Kit";
        Inventory gui = InventoryUtil.createInventory(title, UnrankedGui.getRowSize());

        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            ItemStack icon = ladder.getIcon().clone();
            ItemMeta iconMeta = icon.getItemMeta();
            ItemUtil.hideItemFlags(iconMeta);

            iconMeta.setLore(StringUtil.CC(Arrays.asList("",
                    "&7Click here to select &c" + ladder.getName() + " &7kit for the duel.")));
            icon.setItemMeta(iconMeta);
            gui.addItem(icon);
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers())
            if (online.getOpenInventory().getTitle().equals(title))
                online.updateInventory();

        return gui;
    }

}
