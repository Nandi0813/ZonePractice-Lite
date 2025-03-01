package dev.nandi0813.practice.Manager.Gui.GUIs.Match;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LadderSelector extends GUI
{

    @Getter private final MatchType matchType;
    private final Map<Integer, Ladder> ladderSlots = new HashMap<>();

    public LadderSelector(MatchType matchType)
    {
        super(GUIType.LADDER_SELECTOR);
        this.matchType = matchType;

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", matchType.getName()), (int) Math.ceil(Practice.getLadderManager().getUnrankedLadders().size() / 9.)));

        build();
    }

    @Override
    public void build()
    {
        update();
    }

    @Override
    public void update()
    {
        gui.get(1).clear();
        ladderSlots.clear();

        for (Ladder ladder : Practice.getLadderManager().getLadders())
        {
            if (!ladder.isEnabled()) continue;
            if (ladder.getIcon() == null) continue;

            ItemStack icon = ladder.getIcon().clone();
            ItemMeta iconMeta = icon.getItemMeta();
            ItemUtil.hideItemFlags(iconMeta);

            List<String> lore = new ArrayList<>();
            for (String line : LanguageManager.getList("gui.ladder-selector.item-lore"))
            {
                lore.add(StringUtil.CC(line
                        .replaceAll("%ladderName%", ladder.getName())
                        .replaceAll("%matchTypeName%", matchType.getName())));
            }
            iconMeta.setLore(lore);
            icon.setItemMeta(iconMeta);

            int slot = gui.get(1).firstEmpty();
            ladderSlots.put(slot, ladder);
            gui.get(1).setItem(slot, icon);
        }

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

        if (!ladderSlots.containsKey(slot)) return;
        Ladder ladder = ladderSlots.get(slot);

        if (!ladder.isEnabled())
        {
            update();
            return;
        }

        Arena arena = Practice.getArenaManager().getRandomArena(ladder.isBuild());
        if (arena != null)
        {
            List<Player> matchPlayers = new ArrayList<>(party.getMembers());
            Match match = new Match(matchType, matchPlayers, ladder, false, arena);
            party.setMatch(match);
            match.startMatch();
        }
        else
            player.sendMessage(LanguageManager.getString("party.no-available-arena"));

        player.closeInventory();
    }
}
