package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Event.MatchEndEvent;
import dev.nandi0813.practice.Event.MatchStartEvent;
import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.KnockbackType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Util.KitUtil;
import dev.nandi0813.practice.Manager.Match.Util.KnockbackUtil;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
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

public class MatchListener implements Listener
{

    @EventHandler
    public void onMatchStart(MatchStartEvent e)
    {
        Match match = e.getMatch();

        Practice.getMatchManager().getMatches().put(match.getMatchID(), match);
        Practice.getMatchManager().getLiveMatches().add(match);

        if (match.isRanked())
            for (Player player : match.getPlayers())
                Practice.getMatchManager().getRankedPerDay().put(player, Practice.getMatchManager().getRankedPerDay().get(player) + 1);

        Practice.getGuiManager().searchGUI(GUIType.QUEUE_UNRANKED).update();
        Practice.getGuiManager().searchGUI(GUIType.QUEUE_RANKED).update();
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onMatchEnd(MatchEndEvent e)
    {
        Match match = e.getMatch();

        if (Practice.getPartyManager().getParty(match) != null)
            Practice.getPartyManager().getParty(match).setMatch(null);

        Practice.getMatchManager().getLiveMatches().remove(match);

        Practice.getGuiManager().searchGUI(GUIType.QUEUE_UNRANKED).update();
        Practice.getGuiManager().searchGUI(GUIType.QUEUE_RANKED).update();
    }


    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Profile attackerProfile = Practice.getProfileManager().getProfiles().get(attacker);
            Player target = (Player) e.getEntity();
            Profile targetProfile = Practice.getProfileManager().getProfiles().get(target);

            if (e.getEntity() != null && e.getDamager() != null && attackerProfile.getStatus().equals(ProfileStatus.MATCH) && targetProfile.getStatus().equals(ProfileStatus.MATCH))
            {
                if (Practice.getMatchManager().getLiveMatchByPlayer(attacker).equals(Practice.getMatchManager().getLiveMatchByPlayer(target)))
                {
                    Match match = Practice.getMatchManager().getLiveMatchByPlayer(attacker);

                    if (match.getStatus().equals(MatchStatus.LIVE))
                    {
                        if (!match.getLadder().getKnockbackType().equals(KnockbackType.DEFAULT))
                            KnockbackUtil.setPlayerKnockback(e.getEntity(), match.getLadder().getKnockbackType());
                    }
                    else e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);
            Cuboid cuboid = match.getGameArena().getCuboid();

            Location to1 = e.getTo().clone();
            to1.setY(match.getGameArena().getPosition3().getY());
            if (!cuboid.contains(to1))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getLadder().isBuild() || !match.getStatus().equals(MatchStatus.LIVE))
                e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getLadder().isBuild() || !match.getStatus().equals(MatchStatus.LIVE))
            {
                e.setCancelled(true);
            }
            else
            {
                int buildLimit = match.getGameArena().getPosition1().getBlockY() + ConfigManager.getInt("match-settings.build-limit");
                if (e.getBlock().getLocation().getY() >= buildLimit)
                {
                    player.sendMessage(LanguageManager.getString("match.cant-build"));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBucketEmpty(PlayerBucketEmptyEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getLadder().isBuild() || !match.getStatus().equals(MatchStatus.LIVE))
            {
                e.setCancelled(true);
            }
            else
            {
                int buildLimit = match.getGameArena().getPosition1().getBlockY() + ConfigManager.getInt("match-settings.build-limit");
                if (e.getBlockClicked().getLocation().getY() >= buildLimit)
                {
                    player.sendMessage(LanguageManager.getString("match.cant-build"));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        ItemStack item = player.getInventory().getItemInHand();
        Action action = e.getAction();

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK))
                return;
            Ladder ladder = match.getLadder();

            if (item.getType().equals(Material.BOW) || item.getType().equals(Material.ENDER_PEARL))
            {
                if (!match.getStatus().equals(MatchStatus.LIVE)) e.setCancelled(true);
                if (!item.getType().equals(Material.BOW)) player.updateInventory();
            }
            else if (item.getType().equals(Material.POTION) && match.getStatus().equals(MatchStatus.END))
            {
                e.setCancelled(true);
                player.updateInventory();
            }

            // Kit selector
            if (player.getInventory().getItem(8) != null && player.getInventory().getItem(8).equals(KitUtil.getDefaultKitItem()))
            {
                e.setCancelled(true);

                if (item.equals(KitUtil.getDefaultKitItem()))
                    KitUtil.loadKit(player, ladder);

                else if (player.getInventory().getHeldItemSlot() == 0)
                    KitUtil.loadCustomKit(player, ladder);
            }
        }
    }

    @EventHandler (ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            if (player.getInventory().getItem(8) != null && player.getInventory().getItem(8).equals(KitUtil.getDefaultKitItem()))
                e.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (player.getInventory().getItem(8) != null && player.getInventory().getItem(8).equals(KitUtil.getDefaultKitItem()))
                e.setCancelled(true);
            else
            {
                match.getDroppedItems().add(e.getItemDrop());

                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                {
                    if (!match.getPlayers().contains(onlinePlayer) && !match.getSpectators().contains(onlinePlayer))
                    {
                        Practice.getEntityHider().hideEntity(onlinePlayer, e.getItemDrop());
                    }
                }
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e)
    {
        if (e.getEntity().getShooter() instanceof Player)
        {
            Player player = (Player) e.getEntity().getShooter();
            Profile profile = Practice.getProfileManager().getProfiles().get(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH))
            {
                Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

                if (e.getEntityType() == EntityType.SPLASH_POTION || e.getEntityType() == EntityType.ARROW || e.getEntityType() == EntityType.ENDER_PEARL)
                {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                    {
                        if (!match.getPlayers().contains(onlinePlayer) && !match.getSpectators().contains(onlinePlayer))
                        {
                            Practice.getEntityHider().hideEntity(onlinePlayer, e.getEntity());
                        }
                    }
                }
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerPickItem(PlayerPickupItemEvent e)
    {
        Player player = e.getPlayer();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getStatus().equals(MatchStatus.LIVE))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getLadder().isHunger())
                e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e)
    {
        Player player = (Player) e.getEntity();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            if (!match.getLadder().isRegen() && e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (!match.getLadder().isBuild())
            {
                e.setCancelled(true);
                player.sendMessage(LanguageManager.getString("match.cant-craft"));
            }
        }
    }

}
