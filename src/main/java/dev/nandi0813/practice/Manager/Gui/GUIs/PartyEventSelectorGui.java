package dev.nandi0813.practice.Manager.Gui.GUIs;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Gui.GUIs.Match.LadderSelector;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PartyEventSelectorGui extends GUI
{

    public PartyEventSelectorGui()
    {
        super(GUIType.PARTY_EVENT_SELECTOR);

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("party.game-gui.title"), 1));

        build();
    }

    @Override
    public void build()
    {
        gui.get(1).setItem(2, ItemUtil.createItem(LanguageManager.getString("party.game-gui.split-fight-item.name"), Material.FIREWORK_CHARGE));
        gui.get(1).setItem(6, ItemUtil.createItem(LanguageManager.getString("party.game-gui.party-ffa-item.name"), Material.SLIME_BALL));
    }

    @Override
    public void update()
    {
        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Party party = Practice.getPartyManager().getParty(player);
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (party == null)
        {
            player.closeInventory();
            return;
        }

        if (party.getMembers().size() < 2)
        {
            player.closeInventory();
            player.sendMessage(LanguageManager.getString("party.game-cant-start"));
        }

        if (slot == 2)
            new LadderSelector(MatchType.PARTY_SPLIT).open(player);
        else if (slot == 6)
            new LadderSelector(MatchType.PARTY_FFA).open(player);
    }
}
