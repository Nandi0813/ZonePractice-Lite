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
import org.bukkit.event.player.PlayerTeleportEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EnderpearlListener implements Listener
{

    @EventHandler
    public void enderPearlCooldown(PlayerInteractEvent e)
    {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
        {
            Player player = e.getPlayer();
            Profile profile = Practice.getProfileManager().getProfiles().get(player);
            Match match = Practice.getMatchManager().getLiveMatchByPlayer(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH) && match.getStatus().equals(MatchStatus.LIVE) && e.getItem().getType().equals(Material.ENDER_PEARL))
            {
                int duration = ConfigManager.getInt("match-settings.enderpearl.cooldown");

                if (duration > 0)
                {
                    if (!PlayerCooldown.isActive(player, CooldownObject.ENDER_PEARL))
                    {
                        EnderpearlRunnable enderPearlCountdown = new EnderpearlRunnable(player);
                        enderPearlCountdown.begin();
                    }
                    else
                    {
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

    @EventHandler
    public void enderPearlTpFix(PlayerTeleportEvent e)
    {
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
        {
            e.getTo().setX(Math.floor(e.getTo().getX())+0.5f);
            e.getTo().setY(Math.floor(e.getTo().getY())+0.5f);
            e.getTo().setZ(Math.floor(e.getTo().getZ())+0.5f);

            /*
            BlockCheck landing = new BlockCheck(e.getTo().getBlock());
            boolean cancelTeleport = true;

            if ((e.getFrom().getWorld() == e.getTo().getWorld()) &&  (e.getFrom().distanceSquared(e.getTo()) < 32768))
            {
                cancelTeleport = false;
                if (landing.isSafe)
                {
                    e.getTo().setY(Math.floor(e.getTo().getY())+landing.adjustY);
                }
                else
                {
                    cancelTeleport=true;
                    double xMin = Math.min(e.getFrom().getX(), e.getTo().getX());
                    double xMax = Math.max(e.getFrom().getX(), e.getTo().getX());
                    double yMin = Math.min(e.getFrom().getY(), e.getTo().getY());
                    double yMax = Math.max(e.getFrom().getY(), e.getTo().getY());
                    double zMin = Math.min(e.getFrom().getZ(), e.getTo().getZ());
                    double zMax = Math.max(e.getFrom().getZ(), e.getTo().getZ());
                    List<Location> locations = new ArrayList<Location>();
                    for (double x=xMin; x<xMax; x++)
                        for (double y=yMin; y<yMax; y++)
                            for (double z=zMin; z<zMax; z++)
                                locations.add(new Location(e.getTo().getWorld(), Math.floor(x)+0.5f, Math.floor(y)+0.5f, Math.floor(z)+0.5f));

                    locations.sort(Comparator.comparing(location -> e.getTo().distanceSquared(location)));
                    for (Location location : locations)
                    {
                        BlockCheck blockCheck = new BlockCheck(location.getBlock());
                        if (blockCheck.isSafe)
                        {
                            location.setYaw(e.getTo().getYaw());
                            location.setPitch(e.getTo().getPitch());
                            location.setY(Math.floor(location.getY())+blockCheck.adjustY);
                            e.setTo(location);
                            cancelTeleport = false;
                            break;
                        }
                    }
                }
            }
            if ((cancelTeleport) || (e.getTo().equals(e.getFrom())))
            {
                e.setCancelled(true);
                e.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL,1));
                e.getPlayer().updateInventory();
            }
             */
        }
    }

}
