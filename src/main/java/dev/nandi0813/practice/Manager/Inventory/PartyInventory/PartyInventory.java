package dev.nandi0813.practice.Manager.Inventory.PartyInventory;

import dev.nandi0813.practice.Manager.Inventory.QueueInventory.QueueInventoryListener;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class PartyInventory
{

    @Getter File file = new File(Practice.getInstance().getDataFolder() + "/inventories", "partyitems.yml");
    private final YamlConfiguration config;

    @Getter private static ItemStack hostEventItem;
    @Getter private static ItemStack infoItem;
    @Getter private static ItemStack leaveItem;

    public PartyInventory()
    {
        Bukkit.getPluginManager().registerEvents(new QueueInventoryListener(), Practice.getInstance());
        if (!file.exists()) Practice.getInstance().saveResource("inventories/partyitems.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
        getInventory();
    }

    public void getInventory()
    {
        hostEventItem = (ItemStack) config.get("host-event.item");
        ItemUtil.hideItemFlags(hostEventItem);

        infoItem = (ItemStack) config.get("info.item");
        ItemUtil.hideItemFlags(infoItem);

        leaveItem = (ItemStack) config.get("leave.item");
        ItemUtil.hideItemFlags(leaveItem);
    }

    public void setPartyInventory(Player player)
    {
        player.getInventory().setItem(config.getInt("host-event.slot"), hostEventItem);
        player.getInventory().setItem(config.getInt("info.slot"), infoItem);
        player.getInventory().setItem(config.getInt("leave.slot"), leaveItem);

        player.closeInventory();
    }

}
