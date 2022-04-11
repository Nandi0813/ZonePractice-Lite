package dev.nandi0813.practice.Manager.Party.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class PartyEventsGui
{

    public static Inventory getPartyEventGui()
    {
        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("party.game-gui.title"), 1);

        gui.setItem(2, ItemUtil.createItem("party.game-gui.split-fight-item.name", Material.FIREWORK_CHARGE));
        gui.setItem(6, ItemUtil.createItem("party.game-gui.party-ffa-item.name", Material.SLIME_BALL));

        return gui;
    }

}
