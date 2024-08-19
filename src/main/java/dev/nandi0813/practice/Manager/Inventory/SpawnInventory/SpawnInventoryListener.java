package dev.nandi0813.practice.Manager.Inventory.SpawnInventory;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Gui.GUIs.StatsGui;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnInventoryListener implements Listener
{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        Party party = Practice.getPartyManager().getParty(player);
        ItemStack item = e.getItem();
        Action action = e.getAction();

        if (item != null && profile.getStatus().equals(ProfileStatus.LOBBY) && party == null)
        {
            if (!player.hasPermission("zonepractice.admin"))
                e.setCancelled(true);

            if (action.equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
            {
                if (item.equals(SpawnInventory.getUnrankedItem()))
                {
                    Practice.getGuiManager().searchGUI(GUIType.QUEUE_UNRANKED).open(player);
                }
                else if (item.equals(SpawnInventory.getRankedItem()))
                {
                    int i1 = Practice.getMatchManager().getAllowedRankedPerDay().get(player);
                    int i2 = Practice.getMatchManager().getRankedPerDay().get(player);

                    if (i1 > i2)
                    {
                        Practice.getGuiManager().searchGUI(GUIType.QUEUE_RANKED).open(player);
                    }
                    else
                        player.sendMessage(LanguageManager.getString("match.ranked-limit"));
                }
                else if (item.equals(SpawnInventory.getKiteditorItem()))
                {
                    if (player.hasPermission("zonepractice.customkit"))
                    {
                        Practice.getGuiManager().searchGUI(GUIType.CUSTOM_LADDER_SELECTOR).open(player);
                    }
                    else
                        player.sendMessage(LanguageManager.getString("no-permission"));
                }
                else if (item.equals(SpawnInventory.getPartyItem()))
                {
                    Practice.getPartyManager().createParty(player);
                }
                else if (item.equals(SpawnInventory.getStatsItem()))
                {
                    new StatsGui(profile).open(player);
                }
            }
        }
    }

}