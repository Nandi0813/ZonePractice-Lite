package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Gui.RankedGui;
import dev.nandi0813.practice.Manager.Gui.UnrankedGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Queue.Queue;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        InventoryView inventoryView = e.getView();
        ItemStack item = e.getCurrentItem();
        int slot = e.getRawSlot();

        if (inventoryView.getTitle().equals(UnrankedGui.getGui().getTitle()))
        {
            e.setCancelled(true);
            if (item != null && !item.getType().equals(Material.AIR) && inventoryView.getTopInventory().getSize() > slot)
            {
                for (Ladder ladder : SystemManager.getLadderManager().getLadders())
                {
                    if (item.getItemMeta().getDisplayName().equals(ladder.getIcon().getItemMeta().getDisplayName())
                            && item.getType().equals(ladder.getIcon().getType()))
                    {
                        player.closeInventory();

                        Queue queue = new Queue(player, ladder, false);
                        queue.startQueue();
                        break;
                    }
                }
            }
        }
        else if (inventoryView.getTitle().equals(RankedGui.getGui().getTitle()))
        {
            e.setCancelled(true);
            if (item != null && !item.getType().equals(Material.AIR) && inventoryView.getTopInventory().getSize() > slot)
            {
                for (Ladder ladder : SystemManager.getLadderManager().getLadders())
                {
                    if (item.getItemMeta().getDisplayName().equals(ladder.getIcon().getItemMeta().getDisplayName())
                            && item.getType().equals(ladder.getIcon().getType()))
                    {
                        player.closeInventory();

                        Queue queue = new Queue(player, ladder, true);
                        queue.startQueue();
                        break;
                    }
                }
            }
        }
    }

}
