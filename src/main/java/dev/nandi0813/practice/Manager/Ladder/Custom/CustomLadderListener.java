package dev.nandi0813.practice.Manager.Ladder.Custom;

import dev.nandi0813.practice.Manager.Ladder.Custom.Gui.KitEditorGui;
import dev.nandi0813.practice.Manager.Ladder.Custom.Gui.KitSelectorGui;
import dev.nandi0813.practice.Manager.Ladder.Custom.Gui.KitSumGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CustomLadderListener implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        InventoryView inventoryView = e.getView();
        Inventory inventory = e.getView().getTopInventory();
        int slot = e.getRawSlot();
        ItemStack item = e.getCurrentItem();
        InventoryAction action = e.getAction();

        if (profile.getStatus().equals(ProfileStatus.LOBBY))
        {
            if (inventoryView.getTitle().equals(KitSelectorGui.getGui().getTitle()))
            {
                e.setCancelled(true);
                if (item != null && !item.getType().equals(Material.AIR) && inventoryView.getTopInventory().getSize() > slot)
                {
                    for (Ladder ladder : SystemManager.getLadderManager().getLadders())
                    {
                        if (item.getItemMeta().getDisplayName().equals(ladder.getIcon().getItemMeta().getDisplayName())
                                && item.getType().equals(ladder.getIcon().getType()))
                        {
                            KitSumGui.openGui(player, ladder);
                        }
                    }
                }
            }
        }
        else if (profile.getStatus().equals(ProfileStatus.EDITOR))
        {
            if (CustomLadderManager.getOpenedEditor().containsKey(player))
            {
                Ladder ladder = CustomLadderManager.getOpenedEditor().get(player);

                if (!CustomLadderManager.getOpenedKit().containsKey(player))
                {
                    e.setCancelled(true);
                    if (inventory.getSize() > slot)
                    {
                        if (slot == 4)
                        {
                            KitEditorGui.openGui(player, ladder);
                        }
                        else if (slot == 22 && profile.getCustomKits().containsKey(ladder))
                        {
                            KitEditorGui.openGui(player, ladder);
                        }
                        else if (slot == 31)
                        {
                            profile.getCustomKits().remove(ladder);
                            profile.getFile().deleteCustomKit(ladder);

                            CustomLadderManager.getBackToSum().add(player);
                            KitSumGui.openGui(player, ladder);
                        }
                    }
                    else if (inventory.getSize() < slot)
                    {
                        if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.ARROW))
                        {
                            player.openInventory(KitSelectorGui.getGui());
                            KitSelectorGui.updateGui();
                        }
                    }
                }
                else
                {
                    if (inventory.getSize() > slot && !action.equals(InventoryAction.DROP_ONE_CURSOR) && !action.equals(InventoryAction.DROP_ALL_CURSOR)) e.setCancelled(true);

                    if (slot == 6 || slot == 8)
                    {
                        CustomLadderManager.getBackToSum().add(player);
                        KitSumGui.openGui(player, ladder);
                    }
                    else if (slot == 7)
                        player.getInventory().setContents(ladder.getInventory());
                    else if ((20 <= slot && slot <= 26) || (29 <= slot && slot <= 35) || (38 <= slot && slot <= 44) || (47 <= slot && slot <= 53))
                    {
                        if (item != null && !item.getType().equals(Material.AIR) && !item.equals(KitEditorGui.getFillerItem()))
                            player.setItemOnCursor(item);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e)
    {
        Player player = (Player) e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.EDITOR) && CustomLadderManager.getOpenedEditor().containsKey(player))
        {
            if (CustomLadderManager.getOpenedKit().containsKey(player))
            {
                Ladder ladder = CustomLadderManager.getOpenedEditor().get(player);

                profile.getCustomKits().remove(ladder);
                profile.getCustomKits().put(ladder, player.getInventory().getContents());

                player.getInventory().clear();
            }

            if (!CustomLadderManager.getBackToSum().contains(player))
                SystemManager.getInventoryManager().getSpawnInventory().setInventory(player, false);

            CustomLadderManager.getOpenedEditor().remove(player);
            CustomLadderManager.getOpenedKit().remove(player);
            CustomLadderManager.getBackToSum().remove(player);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.EDITOR) && CustomLadderManager.getOpenedKit().containsKey(player))
        {
            e.getItemDrop().remove();
        }
    }

}
