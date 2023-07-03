package dev.nandi0813.practice.Manager.Gui.GUIs.CustomLadder;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLadderSelectorGui extends GUI
{

    private final Map<Integer, Ladder> ladderSlots = new HashMap<>();

    public CustomLadderSelectorGui()
    {
        super(GUIType.CUSTOM_LADDER_SELECTOR);

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.selector.title"), 1));

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
        gui.get(1).clear();
        ladderSlots.clear();

        for (Ladder ladder : Practice.getLadderManager().getLadders())
        {
            if (ladder.isEnabled() && ladder.isEditable())
            {
                ItemStack icon = ladder.getIcon().clone();
                ItemMeta iconMeta = icon.getItemMeta();
                ItemUtil.hideItemFlags(iconMeta);

                List<String> lore = new ArrayList<>();
                for (String line : LanguageManager.getList("gui.kit-editor.selector.item-lore"))
                    lore.add(line.replaceAll("%ladderName%", ladder.getName()));

                iconMeta.setLore(lore);
                icon.setItemMeta(iconMeta);

                int slot = gui.get(1).firstEmpty();
                ladderSlots.put(slot, ladder);
                gui.get(1).setItem(slot, icon);
            }
        }

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        int slot = e.getRawSlot();

        if (!profile.getStatus().equals(ProfileStatus.LOBBY)) return;

        e.setCancelled(true);

        if (!ladderSlots.containsKey(slot)) return;
        Ladder ladder = ladderSlots.get(slot);

        if (!ladder.isEnabled())
        {
            update();
            return;
        }

        new CustomLadderSumGui(profile, ladder).open(player);
    }

}
