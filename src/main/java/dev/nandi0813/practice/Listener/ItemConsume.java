package dev.nandi0813.practice.Listener;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemConsume implements Listener
{

    @EventHandler (ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        ItemStack item = e.getItem();

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            // Golden Head Listener
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName())
            {
                if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', ConfigManager.getString("match-settings.golden-head")))))
                {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 90, 0), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 9, 1), true);
                }
            }

            // Remove empty potion bottles listener
            if (item != null && item.getType() == Material.POTION && ConfigManager.getConfig().getBoolean("match-settings.remove-empty-bottle"))
            {
                Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () -> player.getInventory().remove(Material.GLASS_BOTTLE), 1L);
            }
        }
    }

}
