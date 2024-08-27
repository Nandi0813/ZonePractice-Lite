package dev.nandi0813.practice.Manager.Gui.GUIs;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsGui extends GUI
{

    @Getter private final Profile profile;

    public StatsGui(Profile profile)
    {
        super(GUIType.PROFILE_STATS);
        this.profile = profile;

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.stats.title").replaceAll("%player%", profile.getPlayer().getName()), 5));

        build();
    }

    @Override
    public void build()
    {
        update();
    }

    @Override
    public void update()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
        {
            DecimalFormat df = new DecimalFormat("0.00");

            List<String> overallLore = new ArrayList<>();
            for (String line : LanguageManager.getList("gui.stats.overall.lore"))
            {
                int totalWins = profile.getUnrankedWins() + profile.getRankedWins();
                int totalLosses = profile.getUnrankedLosses() + profile.getRankedLosses();
                double winLossRatio = totalLosses == 0 ? 0 : totalWins * 1.0 / totalLosses;

                overallLore.add(line
                        .replaceAll("%wins%", String.valueOf(totalWins))
                        .replaceAll("%losses%", String.valueOf(totalLosses))
                        .replaceAll("%w/l-ratio%", df.format(winLossRatio)));
            }
            gui.get(1).setItem(20, ItemUtil.createItem(LanguageManager.getString("gui.stats.overall.name"), Material.DIAMOND, overallLore));


            for (Ladder ladder : Practice.getLadderManager().getLadders())
            {
                if (ladder.isEnabled())
                {
                    ItemStack item = ladder.getIcon().clone();
                    ItemMeta itemMeta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();

                    if (ladder.isRanked())
                    {
                        Integer elo = profile.getElo().get(ladder);
                        Integer unrankedWins = profile.getLadderUnRankedWins().get(ladder);
                        Integer unrankedLosses = profile.getLadderUnRankedLosses().get(ladder);
                        Integer rankedWins = profile.getLadderRankedWins().get(ladder);
                        Integer rankedLosses = profile.getLadderRankedLosses().get(ladder);

                        double ladderWinLossRatio = (rankedWins != null && rankedLosses != null && rankedWins + rankedLosses != 0)
                                ? (rankedWins + unrankedWins) * 1.0 / (rankedLosses + unrankedLosses)
                                : 0;

                        for (String line : LanguageManager.getList("gui.stats.ladder.ranked.lore"))
                        {
                            lore.add(line
                                    .replaceAll("%elo%", elo != null ? String.valueOf(elo) : "0")
                                    .replaceAll("%unrankedWins%", unrankedWins != null ? String.valueOf(unrankedWins) : "0")
                                    .replaceAll("%unrankedLosses%", unrankedLosses != null ? String.valueOf(unrankedLosses) : "0")
                                    .replaceAll("%rankedWins%", rankedWins != null ? String.valueOf(rankedWins) : "0")
                                    .replaceAll("%rankedLosses%", rankedLosses != null ? String.valueOf(rankedLosses) : "0")
                                    .replaceAll("%w/l-ratio%", df.format(ladderWinLossRatio)));
                        }
                    }
                    else
                    {
                        Integer unrankedWins = profile.getLadderUnRankedWins().get(ladder);
                        Integer unrankedLosses = profile.getLadderUnRankedLosses().get(ladder);

                        double ladderWinLossRatio = (unrankedLosses != null && unrankedLosses != 0)
                                ? unrankedWins * 1.0 / unrankedLosses
                                : 0;

                        for (String line : LanguageManager.getList("gui.stats.ladder.unranked.lore"))
                        {
                            lore.add(line
                                    .replaceAll("%unrankedWins%", unrankedWins != null ? String.valueOf(unrankedWins) : "0")
                                    .replaceAll("%unrankedLosses%", unrankedLosses != null ? String.valueOf(unrankedLosses) : "0")
                                    .replaceAll("%w/l-ratio%", df.format(ladderWinLossRatio)));
                        }
                    }

                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);

                    for (int i : new int[]{14,15,16,23,24,25,32,33,34})
                    {
                        if (gui.get(1).getItem(i) == null)
                        {
                            gui.get(1).setItem(i, ItemUtil.hideItemFlags(item));
                            break;
                        }
                    }
                }
            }

            updatePlayers();
        });
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        e.setCancelled(true);
    }
}
