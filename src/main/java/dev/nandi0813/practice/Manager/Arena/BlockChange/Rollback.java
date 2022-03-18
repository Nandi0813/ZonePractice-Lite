package dev.nandi0813.practice.Manager.Arena.BlockChange;

import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Practice;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Rollback
{

    public static void rollBackArena(Match match)
    {

        if (!Practice.getInstance().isEnabled())
        {
            quickRollbackArena(match);
            return;
        }

        final int maxChanges = 50;
        final int maxChecks = 300;

        Iterator<CachedBlock> iterator = new HashSet<>(match.getBlockChange()).iterator();
        List<CachedBlock> dirtToGrassLater = new ArrayList<>();
        match.getBlockChange().clear();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                int changeCounter = 0;
                int checkCounter = 0;

                try
                {
                    while (iterator.hasNext())
                    {
                        if (changeCounter < maxChanges && checkCounter < maxChecks)
                        {
                            CachedBlock l = iterator.next();

                            if (l != null)
                            {
                                if (l.getOldMaterial() == Material.GRASS || l.getOldMaterial() == Material.MYCEL || (l.getOldMaterial() == Material.DIRT && l.getOldData() == 2))
                                {
                                    dirtToGrassLater.add(l);
                                }
                                else
                                {
                                    changeCounter++;
                                    checkCounter++;
                                    l.reset();
                                    Block b = l.getLocation().getBlock();
                                    b.removeMetadata(RollbackListener.PLACED_IN_FIGHT, Practice.getInstance());
                                }
                            }
                            iterator.remove();
                        }
                        else return;
                    }
                }
                catch(Exception e)
                {
                    this.cancel();
                    e.printStackTrace();
                }
                this.cancel();

                // Dirt
                new BukkitRunnable()
                {
                    final Iterator<CachedBlock> iterator = dirtToGrassLater.iterator();

                    @Override
                    public void run()
                    {
                        int changeCounter = 0;
                        int checkCounter = 0;

                        try
                        {
                            while (iterator.hasNext())
                            {
                                if (changeCounter < maxChanges && checkCounter < maxChecks)
                                {
                                    CachedBlock l = iterator.next();

                                    if (l != null)
                                    {
                                        changeCounter++;
                                        checkCounter++;
                                        l.reset();
                                        Block b = l.getLocation().getBlock();
                                        b.removeMetadata(RollbackListener.PLACED_IN_FIGHT, Practice.getInstance());
                                    }
                                    iterator.remove();
                                }
                                else return;
                            }
                        }
                        catch(Exception e)
                        {
                            this.cancel();
                            e.printStackTrace();
                        }
                        this.cancel();
                        match.getGameArena().setAvailable(true);
                    }
                }.runTaskTimer(Practice.getInstance(), 0, 1);
            }
        }.runTaskTimer(Practice.getInstance(), 0, 1);

    }

    public static void quickRollbackArena(Match match)
    {
        Iterator<CachedBlock> iterator = new HashSet<>(match.getBlockChange()).iterator();
        match.getBlockChange().clear();

        while (iterator.hasNext())
        {
            CachedBlock l = iterator.next();
            l.reset();
            Block b = l.getLocation().getBlock();
            b.removeMetadata(RollbackListener.PLACED_IN_FIGHT, Practice.getInstance());
            iterator.remove();
        }

        match.getGameArena().setAvailable(true);
    }

}
