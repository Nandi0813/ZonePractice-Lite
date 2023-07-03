package dev.nandi0813.practice.Manager.Match.Util;

import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtil
{

    /**
     * It sets the player's health, food, fire ticks, max health, fall distance, walk speed, potion effects, game mode, and
     * flying to default values
     *
     * @param player The player to set the match player for.
     */
    public static void setMatchPlayer(Player player)
    {
        player.setHealth(20);
        Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () -> player.setHealth(20), 2L);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.resetMaxHealth();
        player.setFallDistance(0);
        player.setWalkSpeed(0.2F);
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }

    /**
     * It drops all of the items in a player's inventory and hides them from non-match players
     *
     * @param player The player to drop the inventory of.
     * @param match The match that the player is in.
     */
    public static void dropPlayerInventory(Player player, Match match)
    {
        for (ItemStack item : player.getInventory().getContents())
        {
            if (item != null && !item.getType().equals(Material.AIR))
            {
                Item droppedItem = player.getWorld().dropItemNaturally(player.getLocation(), item);
                match.getDroppedItems().add(droppedItem);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                {
                    if (!match.getPlayers().contains(onlinePlayer) && !match.getSpectators().contains(onlinePlayer))
                    {
                        Practice.getEntityHider().hideEntity(onlinePlayer, droppedItem);
                    }
                }
            }
        }
        for (ItemStack item : player.getInventory().getArmorContents())
        {
            if (item != null && !item.getType().equals(Material.AIR))
            {
                Item droppedItem = player.getWorld().dropItemNaturally(player.getLocation(), item);
                match.getDroppedItems().add(droppedItem);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                {
                    if (!match.getPlayers().contains(onlinePlayer) && !match.getSpectators().contains(onlinePlayer))
                    {
                        Practice.getEntityHider().hideEntity(onlinePlayer, droppedItem);
                    }
                }
            }
        }
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
    }

    /**
     * Teleports the player to the position of their team
     *
     * @param player The player to teleport
     * @param match The match that the player is in
     */
    public static void teleportPlayer(Player player, Match match)
    {
        if (match.getTeams().get(player).equals(TeamEnum.TEAM1))
            player.teleport(match.getGameArena().getPosition1());
        else
            player.teleport(match.getGameArena().getPosition2());
    }

    /**
     * Hide a player from all other players in a match
     *
     * @param match The match that the player is in.
     * @param player The player to hide
     */
    public static void hidePlayerPartyGames(Match match, Player player)
    {
        setMatchPlayer(player);
        dropPlayerInventory(player, match);

        for (Player matchPlayer : match.getPlayers())
            matchPlayer.hidePlayer(player);

        player.setAllowFlight(true);
        player.setFlying(true);
        Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () -> player.setFireTicks(0), 2L);
        player.spigot().setCollidesWithEntities(false);
    }

    public static int getPing(Player player)
    {
        return ((CraftPlayer) player).getHandle().ping;
    }

}
