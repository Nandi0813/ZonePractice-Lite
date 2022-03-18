package dev.nandi0813.practice.Manager.Inventory;

import dev.nandi0813.practice.Manager.Inventory.PartyInventory.PartyInventory;
import dev.nandi0813.practice.Manager.Inventory.PartyInventory.PartyInventoryListener;
import dev.nandi0813.practice.Manager.Inventory.QueueInventory.QueueInventory;
import dev.nandi0813.practice.Manager.Inventory.SpawnInventory.SpawnInventory;
import dev.nandi0813.practice.Manager.Inventory.SpectatorInventory.SpectatorInventory;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;

public class InventoryManager
{

    @Getter private final SpawnInventory spawnInventory;
    @Getter private final QueueInventory queueInventory;
    @Getter private final SpectatorInventory spectatorInventory;
    @Getter private final PartyInventory partyInventory;

    public InventoryManager()
    {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), Practice.getInstance());
        Bukkit.getPluginManager().registerEvents(new PartyInventoryListener(), Practice.getInstance());

        spawnInventory = new SpawnInventory();
        queueInventory = new QueueInventory();
        spectatorInventory = new SpectatorInventory();
        partyInventory = new PartyInventory();
    }

}
