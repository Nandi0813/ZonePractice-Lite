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
        else if (inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', MatchType.PARTY_FFA.getName() + "&8 - Kit")) ||
                inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', MatchType.PARTY_SPLIT.getName() + "&8 - Kit")))
        {
            e.setCancelled(true);

            MatchType matchType;
            if (inventoryView.getTitle().equals(ChatColor.translateAlternateColorCodes('&', MatchType.PARTY_FFA.getName() + "&8 - Kit")))
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
