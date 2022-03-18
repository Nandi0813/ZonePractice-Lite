package dev.nandi0813.practice.Manager.Gui;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
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

public class RankedGui
{

    @Getter public static Inventory gui = InventoryUtil.createInventory("&4&lJoin Ranked Queue", getRowSize());

    public static void updateGui()
    {
        gui.clear();
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isRanked())
            {
                ItemStack icon = ladder.getIcon().clone();
                ItemMeta iconMeta = icon.getItemMeta();
                ItemUtil.hideItemFlags(iconMeta);

                int duelMatchSize = SystemManager.getMatchManager().getDuelMatchSize(ladder, true);
                if (duelMatchSize > 0 && duelMatchSize <= 64) icon.setAmount(duelMatchSize);

                iconMeta.setLore(StringUtil.CC(Arrays.asList(
                        "",
                        "  &fIn Queue: &b" + SystemManager.getQueueManager().getQueueSize(ladder, true),
                        "  &fIn Fights: &b" + duelMatchSize,
                        "",
                        "&aClick here to select &cRanked " + ladder.getName() + "&a.")));

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
        List<Ladder> rankedladders = new ArrayList<>();
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isRanked()) rankedladders.add(ladder);
        }
        if (rankedladders.size() > 9)
        {
            if (rankedladders.size() % 9 == 0)
                return rankedladders.size() / 9;
            else
                return rankedladders.size() / 9 + 1;
        }
        return 1;
    }

}
