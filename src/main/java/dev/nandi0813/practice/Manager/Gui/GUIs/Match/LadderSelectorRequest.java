package dev.nandi0813.practice.Manager.Gui.GUIs.Match;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.Cooldown.PlayerCooldown;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LadderSelectorRequest extends GUI
{
    @Getter private final MatchType matchType;
    private final Map<Integer, Ladder> ladderSlots = new HashMap<>();
    private final Player target;

    public LadderSelectorRequest(Player player)
    {
        super(GUIType.LADDER_SELECTOR);
        this.matchType = MatchType.DUEL;

        target = player;

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.ladder-selector.title").replaceAll("%matchTypeName%", player.getName()), 1));

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
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (!ladderSlots.containsKey(slot)) return;
        Ladder ladder = ladderSlots.get(slot);

        if (!ladder.isEnabled())
        {
            update();
            return;
        }

        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        player.sendMessage("You send request " + target.getName());

        TextComponent message = new TextComponent(ChatColor.GREEN + "Click here to accept the duel with " + ChatColor.RED + player.getName());
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + player.getName()));
        target.spigot().sendMessage(message);

        profile.getSendRequests().put(target, ladder);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.getInstance(), () -> profile.getSendRequests().remove(target), ConfigManager.getInt("request-time"));

        player.closeInventory();
    }
}
