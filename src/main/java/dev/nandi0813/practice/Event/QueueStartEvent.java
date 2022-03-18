package dev.nandi0813.practice.Event;

import dev.nandi0813.practice.Manager.Queue.Queue;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QueueStartEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();
    @Getter private final Queue queue;
    private boolean cancelled;

    public QueueStartEvent(Queue queue)
    {
        this.queue = queue;
        cancelled = false;
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

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        cancelled = cancel;
    }

}
