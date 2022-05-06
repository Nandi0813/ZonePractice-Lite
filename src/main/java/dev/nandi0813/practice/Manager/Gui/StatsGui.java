package dev.nandi0813.practice.Manager.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.PlayerUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatsGui
{

    public static Inventory getStatsGui(Profile profile)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("gui.stats.title").replaceAll("%player%", profile.getPlayer().getName()), 5);

        List<String> overallLore = new ArrayList<>();
        for (String line : LanguageManager.getList("gui.stats.overall.lore"))
        {
            overallLore.add(line
                    .replaceAll("%wins%", String.valueOf((profile.getUnrankedWins() + profile.getRankedWins())))
                    .replaceAll("%losses%", String.valueOf((profile.getUnrankedLosses() + profile.getRankedLosses())))
                    .replaceAll("%w/l-ratio%", String.valueOf(df.format((profile.getUnrankedWins() + profile.getRankedWins()) * 1.0 / (profile.getUnrankedLosses() + profile.getRankedLosses())))));
        }
        gui.setItem(20, ItemUtil.createItem(LanguageManager.getString("gui.stats.overall.name"), Material.DIAMOND, overallLore));


        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isEnabled())
            {
                ItemStack item = ladder.getIcon();
                ItemMeta itemMeta = item.getItemMeta();

                List<String> lore = new ArrayList<>();
                for (String line : LanguageManager.getList("gui.stats.ladder.lore"))
                {
                    lore.add(line
                            .replaceAll("%elo%", String.valueOf(profile.getElo().get(ladder)))
                            .replaceAll("%unrankedWins%", String.valueOf(profile.getLadderUnRankedWins().get(ladder)))
                            .replaceAll("%unrankedLosses%", String.valueOf(profile.getLadderUnRankedLosses().get(ladder)))
                            .replaceAll("%rankedWins%", String.valueOf(profile.getLadderRankedWins().get(ladder)))
                            .replaceAll("%rankedLosses%", String.valueOf(profile.getLadderRankedLosses().get(ladder)))
                            .replaceAll("%w/l-ratio%", df.format(((profile.getLadderRankedWins().get(ladder) + profile.getLadderUnRankedWins().get(ladder)) * 1.0 / (profile.getLadderRankedLosses().get(ladder) + profile.getLadderUnRankedLosses().get(ladder))))));
                }

                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);

                for (int i : new int[]{14,15,16,23,24,25,32,33,34})
                    if (gui.getItem(i) == null)
                    {
                        gui.setItem(i, ItemUtil.hideItemFlags(item));
                        break;
                    }
            }
        }

        return gui;
    }

}
