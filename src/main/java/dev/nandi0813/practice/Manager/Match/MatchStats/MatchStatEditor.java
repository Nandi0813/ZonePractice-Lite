package dev.nandi0813.practice.Manager.Match.MatchStats;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;

public class MatchStatEditor
{

    public static void setDuelStats(Profile winner, Profile loser, Ladder ladder, boolean ranked)
    {
        if (ranked)
        {
            winner.getLadderRankedWins().put(ladder, winner.getLadderRankedWins().get(ladder) + 1);
            winner.setRankedWins(winner.getRankedWins() + 1);

            loser.getLadderRankedLosses().put(ladder, loser.getLadderRankedLosses().get(ladder) + 1);
            loser.setRankedLosses(loser.getRankedLosses() + 1);
        }
        else
        {
            winner.getLadderUnRankedWins().put(ladder, winner.getLadderUnRankedWins().get(ladder) + 1);
            winner.setUnrankedWins(winner.getUnrankedWins() + 1);

            loser.getLadderUnRankedLosses().put(ladder, loser.getLadderUnRankedLosses().get(ladder) + 1);
            loser.setUnrankedLosses(loser.getUnrankedLosses() + 1);
        }
    }

}
