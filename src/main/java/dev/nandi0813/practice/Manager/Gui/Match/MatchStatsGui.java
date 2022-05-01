package dev.nandi0813.practice.Manager.Gui.Match;

import dev.nandi0813.practice.Manager.Match.MatchStats.PlayerMatchStat;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.TPSUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MatchStatsGui
{

    public static Inventory createMatchStatsGui(PlayerMatchStat matchStat)
    {
        OfflinePlayer player = matchStat.getPlayer();
        Inventory gui = InventoryUtil.createInventory("&8Inventory of " + player.getName(), 6);

        gui.setContents(matchStat.getEndInventory());

        List<ItemStack> armor = Arrays.asList(matchStat.getEndArmor());
        for (int i = 36; i <= 39; i++)
        {
            gui.setItem(i, armor.get(i - 36));
        }

        ItemStack healthItem = ItemUtil.createItem("&a20.0/" + TPSUtil.round(matchStat.getEndHeart()) + " ♡", Material.SPECKLED_MELON);
        gui.setItem(45, healthItem);

        ItemStack hungerItem = ItemUtil.createItem("&a20.0/" + TPSUtil.round(matchStat.getEndHunger()) + " Hunger", Material.COOKED_BEEF);
        gui.setItem(46, hungerItem);

        return gui;
    }

}
