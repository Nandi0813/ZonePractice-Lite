package dev.nandi0813.practice.Event;

import dev.nandi0813.practice.Manager.Queue.Queue;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QueueEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final Queue queue;

    public QueueEndEvent(Queue queue)
    {
        this.queue = queue;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
