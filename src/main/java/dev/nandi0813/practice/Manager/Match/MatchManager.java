package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.MatchStats.MatchStatListener;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.DuelListener;
import dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA.PartyFFAListener;
import dev.nandi0813.practice.Manager.Match.MatchType.PartySplit.PartySplitListener;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchManager
{

    @Getter private final File folder = new File(Practice.getInstance().getDataFolder() + "/matches");
    @Getter private final HashMap<String, Match> matches = new HashMap<>();
    @Getter private final List<Match> liveMatches = new ArrayList<>();

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

    public Match getLiveMatchById(String matchId)
    {
        for (Match match : liveMatches)
            if (match.getMatchID().equalsIgnoreCase(matchId)) return match;
        return null;
    }

    public Match getLiveMatchByArena(Arena arena)
    {
        for (Match match : liveMatches)
            if (match.getArena().equals(arena)) return match;
        return null;
    }

    public Match getLiveMatchByLadder(Ladder ladder)
    {
        for (Match match : liveMatches)
            if (match.getLadder().equals(ladder)) return match;
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

}
