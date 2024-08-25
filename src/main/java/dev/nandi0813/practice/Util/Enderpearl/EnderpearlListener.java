package dev.nandi0813.practice.Util.Enderpearl;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.Cooldown.CooldownObject;
import dev.nandi0813.practice.Util.Cooldown.PlayerCooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EnderpearlListener implements Listener {

    @EventHandler
    public void enderPearlCooldown(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player player = e.getPlayer();
            Profile profile = Practice.getProfileManager().getProfiles().get(player);
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (profile != null && profile.getStatus() == ProfileStatus.MATCH &&
                    match != null && match.getStatus() == MatchStatus.LIVE) {

                ItemStack item = e.getItem();
                if (item != null && item.getType() == Material.ENDER_PEARL) {
                    int duration = ConfigManager.getInt("match-settings.enderpearl.cooldown");

                    if (duration > 0) {
                        if (!PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL)) {
                            EnderpearlRunnable enderPearlCountdown = new EnderpearlRunnable(player);
                            enderPearlCountdown.begin();
                        } else {
                            e.setCancelled(true);

                            BigDecimal bd = BigDecimal.valueOf(PlayerCooldown.getLeft(player, CooldownObject.ENDER_PEARL) / (float) 1000);
                            bd = bd.setScale(1, RoundingMode.HALF_UP);

                            player.sendMessage(LanguageManager.getString("match.enderpearl-cooldown").replaceAll("%time%", String.valueOf(bd.doubleValue())));
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }
}