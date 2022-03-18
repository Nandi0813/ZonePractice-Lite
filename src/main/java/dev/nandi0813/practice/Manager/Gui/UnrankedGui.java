package dev.nandi0813.practice.Manager.Gui;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnrankedGui
{

    @Getter public static Inventory gui = InventoryUtil.createInventory("&9&lJoin Unranked Queue", getRowSize());

    public static void updateGui()
    {
        gui.clear();
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            ItemStack icon = ladder.getIcon().clone();
            ItemMeta iconMeta = icon.getItemMeta();
            ItemUtil.hideItemFlags(iconMeta);

            int duelMatchSize = SystemManager.getMatchManager().getDuelMatchSize(ladder, false);
            if (duelMatchSize > 0 && duelMatchSize <= 64) icon.setAmount(duelMatchSize);

            iconMeta.setLore(StringUtil.CC(Arrays.asList(
                    "",
                    "  &fIn Queue: &b" + SystemManager.getQueueManager().getQueueSize(ladder, false),
                    "  &fIn Fights: &b" + duelMatchSize,
                    "",
                    "&aClick here to select &eUnranked " + ladder.getName() + "&a.")));

            icon.setItemMeta(iconMeta);
            gui.addItem(icon);
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers())
        {
            if (online.getOpenInventory().getTitle().equals(getGui().getTitle()))
                online.updateInventory();
        }
    }

    public static int getRowSize()
    {
        List<Ladder> unrankedladders = new ArrayList<>(SystemManager.getLadderManager().getLadders());
        if (unrankedladders.size() > 9)
        {
            if (unrankedladders.size() % 9 == 0)
                return unrankedladders.size() / 9;
            else
                return unrankedladders.size() / 9 + 1;
        }
        return 1;
    }

}
