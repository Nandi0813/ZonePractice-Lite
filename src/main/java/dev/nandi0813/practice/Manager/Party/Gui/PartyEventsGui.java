package dev.nandi0813.practice.Manager.Party.Gui;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class PartyEventsGui
{

    /**
     * It creates an inventory with two items, one for split fight and one for party ffa
     *
     * @return An Inventory object.
     */
    public static Inventory getPartyEventGui()
    {
        Inventory gui = InventoryUtil.createInventory(LanguageManager.getString("party.game-gui.title"), 1);

        gui.setItem(2, ItemUtil.createItem(LanguageManager.getString("party.game-gui.split-fight-item.name"), Material.FIREWORK_CHARGE));
        gui.setItem(6, ItemUtil.createItem(LanguageManager.getString("party.game-gui.party-ffa-item.name"), Material.SLIME_BALL));

        return gui;
    }

}
