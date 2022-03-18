package dev.nandi0813.practice.Manager.Party.Gui;

import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class PartyEventsGui
{

    public static Inventory getPartyEventGui()
    {
        Inventory gui = InventoryUtil.createInventory("Party Games", 1);

        gui.setItem(2, ItemUtil.createItem("&bSplit Fight", Material.FIREWORK_CHARGE));
        gui.setItem(6, ItemUtil.createItem("&aParty FFA", Material.SLIME_BALL));

        return gui;
    }

}
