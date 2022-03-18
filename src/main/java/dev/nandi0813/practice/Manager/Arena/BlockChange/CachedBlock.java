package dev.nandi0813.practice.Manager.Arena.BlockChange;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class CachedBlock
{

    @Getter private final Location location;
    @Getter private final Material oldMaterial;
    @Getter private final byte oldData;
    private ItemStack[] chestInventory;

    public CachedBlock(Location location, Block oldBlock)
    {
        this.oldMaterial = oldBlock.getType();
        this.oldData = oldBlock.getData();
        this.location = location;
        saveChest(location);
    }


    public CachedBlock(Location location, Material type, byte data)
    {
        this.oldMaterial = type;
        this.oldData = data;
        this.location = location;
        saveChest(location);
    }

    private void saveChest(Location loc)
    {
        try
        {
            Block block = loc.getBlock();

            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)
            {
                Chest chest = (Chest) block.getState();
                InventoryHolder h = chest.getInventory().getHolder();
                chestInventory = h.getInventory().getContents();

                for (ItemStack is : chestInventory)
                {
                    if (is != null && is.getAmount() <= 0) is.setAmount(is.getAmount());
                }
            }
        }
        catch (Exception e)
        {
            Bukkit.getLogger().info("Arena regen failed to save chest contents");
        }
    }

    public void reset()
    {
        if (location != null && oldMaterial != null)
        {
            location.getBlock().setType(oldMaterial);
            location.getBlock().setData(oldData);
            location.getBlock().getState().update(false);

            if (chestInventory != null && (oldMaterial == Material.CHEST || oldMaterial == Material.TRAPPED_CHEST))
            {
                Block block = location.getBlock();
                Chest chest = (Chest) block.getState();
                InventoryHolder h = chest.getInventory().getHolder();
                h.getInventory().setContents(chestInventory);

                for (ItemStack is : h.getInventory().getContents())
                {
                    if (is != null && is.getAmount() <= 0) is.setAmount(1);
                }
            }
        }
    }

    public static CachedBlock getByLocation(Location l, Collection<CachedBlock> list)
    {
        for (CachedBlock c : list)
            if (l.equals(c.getLocation())) return c;
        return null;
    }



}
