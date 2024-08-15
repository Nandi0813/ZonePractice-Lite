package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.Duel;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Practice;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansion extends PlaceholderExpansion
{

    @Override
    public boolean canRegister()
    {
        return true;
    }

    @Override
    public @NotNull String getIdentifier()
    {
        return "zonepracticelite";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return "Nandi0813";
    }

    @Override
    public @NotNull String getVersion()
    {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier)
    {
        Profile profile = Practice.getProfileManager().getProfiles().get(player);

        if (profile != null)
        {
            if (identifier.contains("boxing_hits_received")) {
                return String.valueOf(profile.receivedHits);
            } else if (identifier.contains("boxing_hits_give")) {
                return String.valueOf(profile.giveHits);
            }

            for (Ladder ladder : Practice.getLadderManager().getLadders())
            {
                String ladderName = ladder.getName().toLowerCase();

                if (identifier.equals("wins_unranked_" + ladderName))
                {
                    return String.valueOf(profile.getLadderUnRankedWins().get(ladder));
                }
                else if (identifier.equals("loses_unranked_" + ladderName))
                {
                    return String.valueOf(profile.getLadderUnRankedLosses().get(ladder));
                }
                else if (identifier.equals("wins_ranked_" + ladderName))
                {
                    return String.valueOf(profile.getLadderRankedWins().get(ladder));
                }
                else if (identifier.equals("loses_ranked_" + ladderName))
                {
                    return String.valueOf(profile.getLadderRankedLosses().get(ladder));
                }
            }
        }

        return null;
    }

}
