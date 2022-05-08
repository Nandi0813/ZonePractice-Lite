package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.MatchStats.MatchStatListener;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.DuelListener;
import dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA.PartyFFAListener;
import dev.nandi0813.practice.Manager.Match.MatchType.PartySplit.PartySplitListener;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

public class MatchManager
{

    @Getter private final HashMap<String, Match> matches = new HashMap<>();
    @Getter private final List<Match> liveMatches = new ArrayList<>();

    @Getter @Setter private HashMap<Player, Integer> rankedPerDay = new HashMap<>();
    @Getter private final HashMap<Player, Integer> allowedRankedPerDay = new HashMap<>();

    public MatchManager(Practice practice)
    {
        Bukkit.getPluginManager().registerEvents(new MatchListener(), practice);
        Bukkit.getPluginManager().registerEvents(new SpectatorListener(), practice);
        Bukkit.getPluginManager().registerEvents(new MatchStatListener(practice), practice);

        Bukkit.getPluginManager().registerEvents(new DuelListener(), practice);
        Bukkit.getPluginManager().registerEvents(new PartyFFAListener(), practice);
        Bukkit.getPluginManager().registerEvents(new PartySplitListener(), practice);
    }

    public Match getLiveMatchByPlayer(Player player)
    {
        for (Match match : liveMatches)
            if (match.getPlayers().contains(player)) return match;
        return null;
    }

    public Match getLiveMatchBySpectator(Player spectator)
    {
        for (Match match : liveMatches)
            if (match.getSpectators().contains(spectator)) return match;
        return null;
    }

    public Match getLiveMatchByArena(Arena arena)
    {
        for (Match match : liveMatches)
            if (match.getArena().equals(arena)) return match;
        return null;
    }

    public int getDuelMatchSize(Ladder ladder, boolean ranked)
    {
        int size = 0;
        for (Match match : liveMatches)
        {
            if (match.getLadder().equals(ladder))
            {
                if (match.isRanked() == ranked) size++;
            }
        }
        return size * 2;
    }

    public int getMatchSize()
    {
        int size = 0;
        for (Match match : liveMatches)
        {
            size = size + match.getPlayers().size();
        }
        return size;
    }

    public void disableLiveMatches()
    {
        for (Match match : liveMatches)
            match.endMatch();
    }


    public void startRankedTimer()
    {
        ZonedDateTime zdt = LocalDate.now(TimeZone.getDefault().toZoneId()).atTime(LocalTime.of(23, 59, 59)).atZone(TimeZone.getDefault().toZoneId());

        long i2 = zdt.toInstant().toEpochMilli()-System.currentTimeMillis();
        long i3 = (i2/1000)*20;

        Bukkit.getScheduler().runTaskTimerAsynchronously(Practice.getInstance(), () ->
        {
            rankedPerDay = new HashMap<>();
            for (Player player : Bukkit.getOnlinePlayers())
                rankedPerDay.put(player, 0);
        }, i3, 86400000L);
    }

}
