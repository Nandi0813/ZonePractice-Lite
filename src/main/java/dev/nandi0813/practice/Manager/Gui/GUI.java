package dev.nandi0813.practice.Manager.Gui;

import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class GUI
{

    @Getter protected final GUIType type;

    @Getter protected final Map<Integer, Inventory> gui = new HashMap<>();
    @Getter protected final Map<Player, Integer> inGuiPlayers = new HashMap<>();

    public GUI(GUIType type)
    {
        this.type = type;
    }

    public abstract void build();
    public abstract void update();

    public void open(Player player, int page)
    {
        if (gui.containsKey(page))
        {
            player.openInventory(gui.get(page));

            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () ->
            {
                inGuiPlayers.put(player, page);
                Practice.getGuiManager().getOpenGUI().put(player, this);
            }, 2L);
        }
        else if (page > 1)
            open(player, page - 1);
        else
            player.closeInventory();
    }

    public void open(Player player)
    {
        open(player, 1);
    }

    public void updatePlayers()
    {
        if (inGuiPlayers.isEmpty()) return;

        for (Player player : inGuiPlayers.keySet())
        {
            if (player != null && player.isOnline() && player.getOpenInventory() != null && inGuiPlayers.get(player) != -1)
                player.updateInventory();
        }
    }

    public void close(Player player)
    {
        inGuiPlayers.remove(player);
        Practice.getGuiManager().getOpenGUI().remove(player);
    }

    public abstract void handleClickEvent(InventoryClickEvent e);

}