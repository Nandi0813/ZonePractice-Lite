package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener
{

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            // Soup listener
            if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR))
            {
                if (item != null && item.getType().equals(Material.MUSHROOM_SOUP))
                {
                    int food = player.getFoodLevel();
                    double health = player.getHealth();
                    double maxHealth = player.getMaxHealth();
                    double regen = 6.5;

                    if (food < 20) e.setCancelled(true);

                    if (health == maxHealth) return;

                    if ((health + regen) < maxHealth)
                    {
                        player.getInventory().getItemInHand().setType(Material.BOWL);
                        player.setHealth(health + regen);
                        player.playSound(player.getLocation(), Sound.EAT, 1.0f, 1.3f);
                    }
                    else if ((health + regen) >= maxHealth)
                    {
                        player.getInventory().getItemInHand().setType(Material.BOWL);
                        player.setHealth(maxHealth);
                        player.playSound(player.getLocation(), Sound.EAT, 1.0f, 1.3f);
                    }
                    player.updateInventory();
                }
            }
        }
    }

}
