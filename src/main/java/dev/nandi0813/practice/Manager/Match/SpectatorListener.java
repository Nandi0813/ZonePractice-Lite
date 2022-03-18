package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.Inventory.SpectatorInventory.SpectatorInventory;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class SpectatorListener implements Listener
{

    @EventHandler (ignoreCancelled=true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Profile attackerProfile = SystemManager.getProfileManager().getProfiles().get(attacker);

            if (attackerProfile.getStatus().equals(ProfileStatus.SPECTATE))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (profile.getStatus().equals(ProfileStatus.SPECTATE))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);
            Cuboid cuboid = match.getGameArena().getCuboid();

            if (!cuboid.contains(e.getTo()))
                player.teleport(match.getGameArena().getPosition3());
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBucketEmpty(PlayerBucketEmptyEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        ItemStack item = player.getInventory().getItemInHand();
        Action action = e.getAction();

        if (profile.isSpectatorMode() || profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
            if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK))
                return;

            Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);

            if (match != null && item.equals(SpectatorInventory.getLeaveItemSpectate()))
            {
                match.removePlayer(player);
            }
        }
    }

    @EventHandler (ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e)
    {
        if (e.getEntity().getShooter() instanceof Player)
        {
            Player player = (Player) e.getEntity().getShooter();
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (profile.getStatus().equals(ProfileStatus.SPECTATE))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerPickItem(PlayerPickupItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);
            match.removePlayer(player);
        }
    }

}
