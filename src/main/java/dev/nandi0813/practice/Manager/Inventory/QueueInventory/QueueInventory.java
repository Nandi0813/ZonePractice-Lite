package dev.nandi0813.practice.Manager.Inventory.QueueInventory;

import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class QueueInventory
{

    @Getter File file = new File(Practice.getInstance().getDataFolder() + "/inventories", "queueitems.yml");
    private final YamlConfiguration config;

    @Getter private static ItemStack leaveItem;

    public QueueInventory()
    {
        Bukkit.getPluginManager().registerEvents(new QueueInventoryListener(), Practice.getInstance());
        if (!file.exists()) Practice.getInstance().saveResource("inventories/queueitems.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
        getInventory();
    }

    public void getInventory()
    {
        leaveItem = (ItemStack) config.get("leave.item");
        ItemUtil.hideItemFlags(leaveItem);
    }

    public void setQueueInventory(Player player)
    {
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        player.getInventory().setItem(config.getInt("leave.slot"), leaveItem);

        player.closeInventory();
    }

}
