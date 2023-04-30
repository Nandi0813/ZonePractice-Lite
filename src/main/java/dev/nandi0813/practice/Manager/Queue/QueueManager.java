package dev.nandi0813.practice.Manager.Queue;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QueueManager
{

    @Getter private final List<Queue> queues = new ArrayList<>();

    public QueueManager(Practice practice)
    {
        Bukkit.getPluginManager().registerEvents(new QueueListener(), practice);
    }

    public Queue getQueue(Player queuePlayer)
    {
        for (Queue queue : queues)
            if (queue.getPlayer() == queuePlayer) return queue;
        return null;
    }

    public int getQueueSize(Ladder ladder, boolean ranked)
    {
        int size = 0;
        for (Queue queue : queues)
            if (queue.getLadder().equals(ladder) && queue.isRanked() == ranked) size++;
        return size;
    }

}
