package dev.nandi0813.practice.Manager.Gui.GUIs.Match;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Match.MatchStats.PlayerMatchStat;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchStatsGui extends GUI
{

    private final PlayerMatchStat matchStat;

    public MatchStatsGui(PlayerMatchStat matchStat)
    {
        super(GUIType.MATCH_STATS);

        this.matchStat = matchStat;
        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.end-match-inventory.title").replaceAll("%player%", matchStat.getPlayer().getName()), 6));

        build();
    }

    @Override
    public void build()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), this::update);
    }

    @Override
    public void update()
    {
        gui.get(1).clear();
        DecimalFormat df = new DecimalFormat("0.0");

        gui.get(1).setContents(matchStat.getEndInventory());

        List<ItemStack> armor = Arrays.asList(matchStat.getEndArmor());
        Collections.reverse(armor);
        for (int i = 36; i <= 39; i++)
        {
            gui.get(1).setItem(i, armor.get(i - 36));
        }

        ItemStack healthItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.health-item-name").replaceAll("%health%", String.valueOf(df.format(matchStat.getEndHeart()))), Material.SPECKLED_MELON);
        gui.get(1).setItem(45, healthItem);

        ItemStack hungerItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.hunger-item-name").replaceAll("%hunger%", String.valueOf(df.format(matchStat.getEndHunger()))), Material.COOKED_BEEF);
        gui.get(1).setItem(46, hungerItem);

        List<String> statsItemLore = new ArrayList<>();
        for (String line : LanguageManager.getList("gui.end-match-inventory.player-stats-item.lore"))
        {
            statsItemLore.add(line
                    .replaceAll("%hits%", String.valueOf(matchStat.getHit()))
                    .replaceAll("%getHit%", String.valueOf(matchStat.getGetHit()))
                    .replaceAll("%combo%", String.valueOf(matchStat.getLongestCombo()))
                    .replaceAll("%cps%", String.valueOf(df.format(matchStat.getAverageCPS()))));
        }
        ItemStack statsItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.player-stats-item.name"), Material.PAPER, statsItemLore);
        gui.get(1).setItem(47, statsItem);

        List<String> potionItemLore = new ArrayList<>();
        for (PotionEffect pe : matchStat.getEndPotionEffects())
        {
            potionItemLore.add(LanguageManager.getString("gui.end-match-inventory.effects-item.effect-line")
                    .replaceAll("%effectName%", getPotionEffectNormalName(pe.getType().getName()))
                    .replaceAll("%effectAmplifier%", String.valueOf((pe.getAmplifier() + 1)))
                    .replaceAll("%timeLeft%", StringUtil.formatMillisecondsToMinutes((pe.getDuration()/20)* 1000L)));
        }
        ItemStack potionItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.effects-item.name"), Material.POTION, potionItemLore);
        gui.get(1).setItem(48, potionItem);

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        e.setCancelled(true);
    }

    public static String getPotionEffectNormalName(String base)
    {
        return WordUtils.capitalize(base.replaceAll("_", "").toLowerCase());
    }

}
