package dev.nandi0813.practice.Manager.Inventory.QueueInventory;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class QueueInventoryListener implements Listener
{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        ItemStack item = e.getItem();
        Action action = e.getAction();

        if (item != null && profile.getStatus().equals(ProfileStatus.QUEUE))
        {
            if (!player.hasPermission("zonepractice.admin"))
                e.setCancelled(true);

            if (action.equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
            {
                if (item.equals(QueueInventory.getLeaveItem()))
                    Practice.getQueueManager().getQueue(player).endQueue(false);
            }
        }
    }

}
