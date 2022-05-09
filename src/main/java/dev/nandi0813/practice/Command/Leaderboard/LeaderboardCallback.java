package dev.nandi0813.practice.Command.Leaderboard;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public interface LeaderboardCallback
{

    void onQueryDone(HashMap<OfflinePlayer, Integer> result);

}
