package dev.nandi0813.practice.Manager.Gui.GUIs;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Queue.Queue;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankedGui extends GUI
{

    private final Map<Integer, Ladder> ladderSlots = new HashMap<>();

    public RankedGui()
    {
        super(GUIType.QUEUE_RANKED);
        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.ranked.title"),
                (int) Math.ceil(Practice.getLadderManager().getRankedLadders().size() / 9.)));

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

        for (Ladder ladder : Practice.getLadderManager().getRankedLadders())
        {
            ItemStack icon = ladder.getIcon().clone();
            ItemMeta iconMeta = icon.getItemMeta();
            ItemUtil.hideItemFlags(iconMeta);

            int duelMatchSize = Practice.getMatchManager().getDuelMatchSize(ladder, true);
            if (duelMatchSize > 0 && duelMatchSize <= 64) icon.setAmount(duelMatchSize);

            List<String> lore = new ArrayList<>();
            for (String line : LanguageManager.getList("gui.ranked.item-lore"))
                lore.add(line
                        .replaceAll("%inQueue%", String.valueOf(Practice.getQueueManager().getQueueSize(ladder, true)))
                        .replaceAll("%inMatch%", String.valueOf(duelMatchSize))
                        .replaceAll("%ladderName%", ladder.getName()));
            iconMeta.setLore(lore);

            icon.setItemMeta(iconMeta);

            int slot = gui.get(1).firstEmpty();
            ladderSlots.put(slot, ladder);
            gui.get(1).setItem(slot, icon);
        }

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        InventoryView inventoryView = e.getView();
        ItemStack item = e.getCurrentItem();
        int slot = e.getRawSlot();

        e.setCancelled(true);
        if (item == null || item.getType().equals(Material.AIR)) return;
        if (inventoryView.getTopInventory().getSize() <= slot) return;

        if (!ladderSlots.containsKey(slot)) return;
        Ladder ladder = ladderSlots.get(slot);

        if (!ladder.isEnabled())
        {
            update();
            return;
        }

        player.closeInventory();
        Queue queue = new Queue(player, ladder, true);
        queue.startQueue();
    }
}
