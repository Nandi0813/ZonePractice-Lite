package dev.nandi0813.practice.Manager.Arena.BlockChange;

import dev.nandi0813.practice.Manager.Arena.Util.Cuboid;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Practice;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public class RollbackListener implements Listener
{

    public static String PLACED_IN_FIGHT = "zonePracticePlacedInFight";
    private final Practice practice;

    public RollbackListener(Practice practice)
    {
        this.practice = practice;
    }

    @EventHandler(priority= EventPriority.HIGHEST,ignoreCancelled=true)
    public void onBlockFromTo(BlockFromToEvent e)
    {
        if (e.getBlock().hasMetadata(PLACED_IN_FIGHT))
        {
            MetadataValue mv = this.getMetadata(e.getBlock(), PLACED_IN_FIGHT);
            if (mv != null && mv.value() != null && mv.value() instanceof Match)
            {
                Match match = (Match) mv.value();
                if (match.getStatus().equals(MatchStatus.OLD))
                    e.setCancelled(true);
                else if (!e.getToBlock().getType().isSolid())
                {
                    e.getToBlock().setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(practice, match));
                    match.addBlockChange(new CachedBlock(e.getToBlock().getLocation(), e.getToBlock().getType(), e.getToBlock().getData()));

                    Block b2 = e.getToBlock().getLocation().subtract(0, 1, 0).getBlock();
                    if (turnsToDirt(b2))
                        match.addBlockChange(new CachedBlock(b2.getLocation(), b2));
                }
            }
        }
        else
        {
            for (BlockFace face : BlockFace.values())
            {
                Block b = e.getBlock().getRelative(face, 1);
                if (b.hasMetadata(PLACED_IN_FIGHT))
                {
                    MetadataValue mv = getMetadata(b, PLACED_IN_FIGHT);
                    if (mv != null && mv.value() != null && mv.value() instanceof Match)
                    {
                        Match match = (Match) mv.value();
                        if (match != null && !e.getToBlock().getType().isSolid())
                        {
                            e.getToBlock().setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(practice, match));
                            match.addBlockChange(new CachedBlock(e.getToBlock().getLocation(), e.getToBlock().getType(), e.getToBlock().getData()));

                            Block b2 = e.getToBlock().getLocation().subtract(0, 1, 0).getBlock();
                            if (turnsToDirt(b2))
                                match.addBlockChange(new CachedBlock(b2.getLocation(), b2));
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
    public void onBucketEmpty(PlayerBucketEmptyEvent e)
    {
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(e.getPlayer());

        if (match != null)
        {
            e.getBlockClicked().getRelative(e.getBlockFace()).setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(practice, match));

            for (BlockFace face : BlockFace.values())
            {
                Block block = e.getBlockClicked().getRelative(face, 1);

                if (block.hasMetadata(PLACED_IN_FIGHT))
                {
                    MetadataValue mv = getMetadata(block, PLACED_IN_FIGHT);
                    if (mv != null && mv.value() != null && mv.value() instanceof Match)
                    {
                        if (!block.getType().isSolid())
                        {
                            block.setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(practice, match));
                            match.addBlockChange(new CachedBlock(block.getLocation(), block.getType(), block.getData()));

                            Block b2 = block.getLocation().subtract(0, 1, 0).getBlock();
                            if (turnsToDirt(b2))
                                match.addBlockChange(new CachedBlock(b2.getLocation(), b2));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if (e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST))
            {
                Match match = Practice.getMatchManager().getLiveMatchByPlayer(e.getPlayer());

                if(match != null)
                    match.addBlockChange(new CachedBlock(e.getClickedBlock().getLocation(), e.getClickedBlock()));
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
    public void onPlace(BlockPlaceEvent e)
    {
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(e.getPlayer());
        if (match != null)
        {
            e.getBlockPlaced().setMetadata(PLACED_IN_FIGHT, new FixedMetadataValue(practice, match));

            match.addBlockChange(new CachedBlock(e.getBlockPlaced().getLocation(), e.getBlockReplacedState().getType(), e.getBlockReplacedState().getRawData()));
            Block b2 = e.getBlockPlaced().getLocation().subtract(0, 1, 0).getBlock();
            if(turnsToDirt(b2))
                match.addBlockChange(new CachedBlock(b2.getLocation(), b2));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
    public void onBreak(BlockBreakEvent e)
    {
        Match match = Practice.getMatchManager().getLiveMatchByPlayer(e.getPlayer());
        if (match != null)
        {
            if (e.getBlock().hasMetadata(PLACED_IN_FIGHT))
            {
                MetadataValue mv = getMetadata(e.getBlock(), PLACED_IN_FIGHT);
                if (mv != null && mv.value() != null && mv.value() instanceof Match)
                {
                    match.addBlockChange(new CachedBlock(e.getBlock().getLocation(), e.getBlock()));
                    Block b2 = e.getBlock().getLocation().subtract(0, 1, 0).getBlock();
                    if (b2.getType() == Material.DIRT)
                        match.addBlockChange(new CachedBlock(b2.getLocation(), b2));
                }
                else
                    e.setCancelled(true);
            }
            else
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e)
    {
        Location location = e.getLocation();

        for (Cuboid cuboid : Practice.getArenaManager().getArenaCuboids().keySet())
        {
            if (cuboid.contains(location))
            {
                e.setCancelled(true);
                for (Block block : e.blockList())
                {
                    MetadataValue mv = getMetadata(block, PLACED_IN_FIGHT);
                    if (mv != null && mv.value() != null && mv.value() instanceof Match)
                    {
                        if (!block.getType().equals(Material.TNT))
                            block.breakNaturally();
                        else
                        {
                            block.setType(Material.AIR);
                            location.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
                        }
                    }
                }
                return;
            }
        }
    }


    public static boolean turnsToDirt(Block b)
    {
        return b.getType() == Material.GRASS || b.getType() == Material.MYCEL || (b.getType() == Material.DIRT && b.getData() == 2);
    }

    public MetadataValue getMetadata(Metadatable m, String tag)
    {
        for (MetadataValue mv : m.getMetadata(tag))
            if (mv != null && mv.getOwningPlugin() != null && mv.getOwningPlugin() == practice)
            {
                return mv;
            }
        return null;
    }

}
