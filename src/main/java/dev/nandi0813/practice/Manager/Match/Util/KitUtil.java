package dev.nandi0813.practice.Manager.Match.Util;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitUtil
{

    @Getter public static ItemStack defaultKitItem = ItemUtil.createItem("&e&lDefault Kit", Material.ENCHANTED_BOOK);

    /**
     * It sets the player's armor and inventory to the armor and inventory of the ladder
     *
     * @param player The player you want to load the kit for.
     * @param ladder The ladder you want to load the kit for.
     */
    public static void loadKit(Player player, Ladder ladder)
    {
        player.getInventory().setArmorContents(ladder.getArmor());
        player.getInventory().setContents(ladder.getInventory());
        player.updateInventory();
    }

    /**
     * It loads a custom kit for a player
     *
     * @param player The player you want to load the kit for.
     * @param ladder The ladder you want to load the kit for.
     */
    public static void loadCustomKit(Player player, Ladder ladder)
    {
        player.getInventory().setArmorContents(ladder.getArmor());
        player.getInventory().setContents(Practice.getProfileManager().getProfiles().get(player).getCustomKits().get(ladder));
        player.updateInventory();
    }

}
