package dev.nandi0813.practice.Manager.Match.Util;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitUtil
{

    @Getter public static ItemStack defaultKitItem = ItemUtil.createItem("&e&lDefault Kit", Material.ENCHANTED_BOOK);

    public static void loadKit(Player player, Ladder ladder)
    {
        player.getInventory().setArmorContents(ladder.getArmor());
        player.getInventory().setContents(ladder.getInventory());
        player.updateInventory();
    }

    public static void loadCustomKit(Player player, Ladder ladder)
    {
        player.getInventory().setArmorContents(ladder.getArmor());
        player.getInventory().setContents(SystemManager.getProfileManager().getProfiles().get(player).getCustomKits().get(ladder));
        player.updateInventory();
    }

}
