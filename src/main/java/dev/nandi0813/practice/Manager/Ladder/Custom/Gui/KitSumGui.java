package dev.nandi0813.practice.Manager.Ladder.Custom.Gui;

import dev.nandi0813.practice.Manager.Ladder.Custom.CustomLadderManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class KitSumGui
{

    public static void openGui(Player player, Ladder ladder)
    {
        Inventory gui = InventoryUtil.createInventory("&8Viewing " + ladder.getName() + " kit", 4);
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        player.openInventory(gui);
        player.getInventory().clear();

        // Set kit columns
        if (profile.getCustomKits().get(ladder) != null)
        {
            gui.setItem(4, ItemUtil.createItem("&6&lKit",Material.GOLD_SWORD));

            gui.setItem(13, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));
            gui.setItem(22, ItemUtil.createItem("&a&lEdit Kit", Material.BOOK));
            gui.setItem(31, ItemUtil.createItem("&c&lDelete Kit", Material.REDSTONE, getDeleteItemLore()));
        }
        else
        {
            gui.setItem(4, ItemUtil.createItem("&a&lCreate Kit",Material.STONE_SWORD));

            gui.setItem(13, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));
            gui.setItem(22, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));
            gui.setItem(31, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));
        }

        // Set filler items between kits
        for (int i = 0; i < gui.getSize(); i++)
        {
            if (gui.getItem(i) == null)
                gui.setItem(i, CustomLadderManager.getFillerItem());
        }

        // Set go back items to players inventory
        buildPlayerInventory(player);

        // Set players status details
        CustomLadderManager.getOpenedEditor().put(player, ladder);
        profile.setStatus(ProfileStatus.EDITOR);
    }

    public static void buildPlayerInventory(Player player)
    {
        for (int i = 0; i < 9; i++)
            player.getInventory().setItem(i, ItemUtil.createItem("&cBack to kit selector", Material.ARROW));
    }


    public static ArrayList<String> getDeleteItemLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&c&lYou won't be able to");
        lore.add("&crecover this kit.");
        lore.add("");
        return lore;
    }

}
