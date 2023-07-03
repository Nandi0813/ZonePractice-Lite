package dev.nandi0813.practice.Manager.Gui;

import dev.nandi0813.practice.Manager.Gui.GUIs.PartyEventSelectorGui;
import dev.nandi0813.practice.Manager.Gui.GUIs.RankedGui;
import dev.nandi0813.practice.Manager.Gui.GUIs.UnrankedGui;
import dev.nandi0813.practice.Manager.Gui.GUIs.CustomLadder.CustomLadderEditorGui;
import dev.nandi0813.practice.Manager.Gui.GUIs.CustomLadder.CustomLadderSelectorGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIManager implements Listener
{

    @Getter private final List<GUI> guis = new ArrayList<>();
    @Getter private final Map<Player, GUI> openGUI = new HashMap<>();

    @Getter private static final ItemStack fillerItem = ItemUtil.createItem(" ", Material.STAINED_GLASS_PANE, Short.valueOf("15"));
    @Getter private static final ItemStack dummyItem = ItemUtil.createItem("DUMMY", Material.GLOWSTONE_DUST);

    public GUIManager()
    {
        Bukkit.getPluginManager().registerEvents(this, Practice.getInstance());
    }

    public void buildGUIs()
    {
        guis.add(new UnrankedGui());
        guis.add(new RankedGui());
        guis.add(new PartyEventSelectorGui());
        guis.add(new CustomLadderSelectorGui());
    }

    public GUI searchGUI(GUIType type)
    {
        for (GUI gui : guis)
            if (gui.getType().equals(type))
                return gui;
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        GUI gui = openGUI.get(player);
        if (gui == null) return;

        gui.handleClickEvent(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e)
    {
        if (!(e.getPlayer() instanceof Player)) return;
        Player player = (Player) e.getPlayer();

        GUI gui = openGUI.get(player);
        if (gui == null) return;

        if (gui instanceof CustomLadderEditorGui)
        {
            CustomLadderEditorGui customLadderEditorGui = (CustomLadderEditorGui) gui;
            Ladder ladder = customLadderEditorGui.getLadder();

            if (ladder.isEnabled() && ladder.isEditable())
                customLadderEditorGui.getProfile().getCustomKits().put(ladder, player.getInventory().getContents());

            player.getInventory().clear();

            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () ->
                    Practice.getInventoryManager().getSpawnInventory().setInventory(player, false),
                    3L);
        }

        // Closing the gui for the player.
        gui.close(player);
    }

    @EventHandler
    public void onLadderEditorItemDrop(PlayerDropItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        GUI gui = openGUI.get(player);

        if (!profile.getStatus().equals(ProfileStatus.EDITOR)) return;
        if (gui == null) return;

        if (gui instanceof CustomLadderEditorGui)
            e.getItemDrop().remove();
    }

}