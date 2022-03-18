package dev.nandi0813.practice.Event;

import dev.nandi0813.practice.Manager.Match.Match;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MatchEndEvent extends Event
{

    private static final HandlerList handlers = new HandlerList();
    @Getter private final Match match;

    public MatchEndEvent(Match match)
    {
        this.match = match;
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
