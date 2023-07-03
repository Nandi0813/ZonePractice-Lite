package dev.nandi0813.practice.Manager.Gui.GUIs.CustomLadder;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CustomLadderSumGui extends GUI
{

    private final Profile profile;
    private final Ladder ladder;

    public CustomLadderSumGui(Profile profile, Ladder ladder)
    {
        super(GUIType.CUSTOM_LADDER_SUMMARY);
        this.profile = profile;
        this.ladder = ladder;

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.summary.title").replaceAll("%ladderName%", ladder.getName()), 1));

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
        Inventory inventory = gui.get(1);
        inventory.clear();

        if (profile.getCustomKits().containsKey(ladder) && profile.getCustomKits().get(ladder) != null)
        {
            inventory.setItem(0, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.existing-kit-item-name"),Material.GOLD_SWORD));

            inventory.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE, Short.valueOf("7")));

            inventory.setItem(2, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.edit-kit-item-name"), Material.BOOK));
            inventory.setItem(3, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.delete-kit-item-name"), Material.REDSTONE, getDeleteItemLore()));
        }
        else
        {
            inventory.setItem(0, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.summary.create-kit-item-name"),Material.STONE_SWORD));
        }

        inventory.setItem(8, ItemUtil.createItem("&cBack to kit selector", Material.ARROW));

        // Set filler items between kits
        for (int i = 0; i < inventory.getSize(); i++)
        {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, GUIManager.getFillerItem());
        }

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (inventory.getSize() > slot)
        {
            if (slot == 0)
            {
                new CustomLadderEditorGui(profile, ladder, this).open(player);
            }
            else if (slot == 2 && profile.getCustomKits().containsKey(ladder))
            {
                new CustomLadderEditorGui(profile, ladder, this).open(player);
            }
            else if (slot == 3)
            {
                profile.getCustomKits().remove(ladder);
                profile.getFile().deleteCustomKit(ladder);

                update();
            }
            else if (slot == 8)
            {
                Practice.getGuiManager().searchGUI(GUIType.CUSTOM_LADDER_SELECTOR).open(player);
            }
        }
    }

    public static List<String> getDeleteItemLore()
    {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&c&lYou won't be able to");
        lore.add("&crecover this kit.");
        return lore;
    }
}
