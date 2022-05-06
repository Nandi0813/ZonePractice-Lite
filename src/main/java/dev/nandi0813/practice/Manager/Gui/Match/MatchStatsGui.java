package dev.nandi0813.practice.Manager.Gui.Match;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.MatchStats.PlayerMatchStat;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchStatsGui
{

    /**
     * It creates an inventory with the player's end inventory, armor, health, hunger, stats, and potion effects
     *
     * @param matchStat The PlayerMatchStat object that you want to create the GUI for.
     * @return An Inventory
     */
    public static Inventory createMatchStatsGui(PlayerMatchStat matchStat)
    {
        DecimalFormat df = new DecimalFormat("0.0");

        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("gui.end-match-inventory.title").replaceAll("%player%", matchStat.getPlayer().getName()), 6);

        gui.setContents(matchStat.getEndInventory());

        List<ItemStack> armor = Arrays.asList(matchStat.getEndArmor());
        Collections.reverse(armor);
        for (int i = 36; i <= 39; i++)
        {
            gui.setItem(i, armor.get(i - 36));
        }

        ItemStack healthItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.health-item-name").replaceAll("%health%", String.valueOf(df.format(matchStat.getEndHeart()))), Material.SPECKLED_MELON);
        gui.setItem(45, healthItem);

        ItemStack hungerItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.hunger-item-name").replaceAll("%hunger%", String.valueOf(df.format(matchStat.getEndHunger()))), Material.COOKED_BEEF);
        gui.setItem(46, hungerItem);

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
        gui.setItem(47, statsItem);

        List<String> potionItemLore = new ArrayList<>();
        for (PotionEffect pe : matchStat.getEndPotionEffects())
        {
            potionItemLore.add(LanguageManager.getString("gui.end-match-inventory.effects-item.effect-line")
                    .replaceAll("%effectName%", getPotionEffectNormalName(pe.getType().getName()))
                    .replaceAll("%effectAmplifier%", String.valueOf((pe.getAmplifier() + 1)))
                    .replaceAll("%timeLeft%", StringUtil.formatMillisecondsToMinutes((pe.getDuration()/20)* 1000L)));
        }
        ItemStack potionItem = ItemUtil.createItem(LanguageManager.getString("gui.end-match-inventory.effects-item.name"), Material.POTION, potionItemLore);
        gui.setItem(48, potionItem);

        return gui;
    }

    public static String getPotionEffectNormalName(String base)
    {
        return WordUtils.capitalize(base.replaceAll("_", "").toLowerCase());
    }

}
