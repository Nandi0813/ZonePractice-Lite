package dev.nandi0813.practice.Manager.Party;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.Match.LadderSelector;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Party.Gui.PartyEventsGui;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PartyListener implements Listener
{

    /**
     * When a player quits, if they are in a party, remove them from the party
     *
     * @param e The event that is being listened for.
     */
    @EventHandler (ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Party party = SystemManager.getPartyManager().getParty(player);

        if (party != null)
        {
            party.removeMember(player, false);
        }
    }

    /**
     * If the player is in a party, and the inventory they clicked on is the party events gui, and the slot they clicked on
     * is either 2 or 6, then open the ladder selector gui. If the inventory they clicked on is the ladder selector gui,
     * and the slot they clicked on is a ladder, then start a match with the party
     *
     * @param e The event that was called.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Party party = SystemManager.getPartyManager().getParty(player);
        InventoryView inventoryView = e.getView();
        ItemStack item = e.getCurrentItem();
        int slot = e.getRawSlot();

        if (party == null)
            return;

        if (inventoryView.getTitle().equalsIgnoreCase(PartyEventsGui.getPartyEventGui().getTitle()))
        {
            e.setCancelled(true);

            if (slot == 2)
            {
                player.openInventory(LadderSelector.getGui(MatchType.PARTY_SPLIT));
            }
            else if (slot == 6)
            {
                player.openInventory(LadderSelector.getGui(MatchType.PARTY_FFA));
            }
        }
        else if (inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", MatchType.PARTY_FFA.getName()))) ||
                inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", MatchType.PARTY_SPLIT.getName()))))
        {
            e.setCancelled(true);

            MatchType matchType;
            if (inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", MatchType.PARTY_FFA.getName()))))
                matchType = MatchType.PARTY_FFA;
            else
                matchType = MatchType.PARTY_SPLIT;


            if (item != null && !item.getType().equals(Material.AIR) && inventoryView.getTopInventory().getSize() > slot)
            {
                if (party.getMembers().size() >= 2)
                {
                    for (Ladder ladder : SystemManager.getLadderManager().getLadders())
                    {
                        if (item.getItemMeta().getDisplayName().equals(ladder.getIcon().getItemMeta().getDisplayName())
                                && item.getType().equals(ladder.getIcon().getType()))
                        {
                            player.closeInventory();

                            Arena arena = SystemManager.getArenaManager().getRandomArena(ladder.isBuild());
                            if (arena != null)
                            {
                                List<Player> matchPlayers = new ArrayList<>(party.getMembers());
                                Match match = new Match(matchType, matchPlayers, ladder, false, arena);
                                party.setMatch(match);
                                match.startMatch();
                            }
                            else
                                player.sendMessage(LanguageManager.getString("party.no-available-arena"));
                            return;
                        }
                    }
                }
                else
                {
                    player.closeInventory();
                    player.sendMessage(LanguageManager.getString("party.game-cant-start"));
                }
            }
        }
    }

}
