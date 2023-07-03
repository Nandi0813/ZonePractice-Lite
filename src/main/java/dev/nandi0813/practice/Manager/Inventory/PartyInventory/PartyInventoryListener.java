package dev.nandi0813.practice.Manager.Inventory.PartyInventory;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PartyInventoryListener implements Listener
{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        Party party = Practice.getPartyManager().getParty(player);
        ItemStack item = e.getItem();
        Action action = e.getAction();

        if (item == null) return;
        if (!profile.getStatus().equals(ProfileStatus.LOBBY)) return;
        if (party == null) return;

        if (!player.hasPermission("zonepractice.admin"))
            e.setCancelled(true);

        if (!action.equals(Action.RIGHT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_AIR)) return;

        if (item.equals(PartyInventory.getInfoItem()))
        {
            player.performCommand("party info");
        }
        else if (item.equals(PartyInventory.getLeaveItem()))
        {
            party.removeMember(player, false);
        }
        else if (item.equals(PartyInventory.getHostEventItem()))
        {
            if (party.getLeader().equals(player))
            {
                if (party.getMembers().size() >= 2)
                    Practice.getGuiManager().searchGUI(GUIType.PARTY_EVENT_SELECTOR).open(player);
                else
                    player.sendMessage(LanguageManager.getString("party.game-cant-start"));
            }
            else
                player.sendMessage(LanguageManager.getString("party.not-leader"));
        }
    }

}
