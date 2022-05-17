package dev.nandi0813.practice.Manager.Match.Util;

import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;

public class TeamUtil
{

    /**
     * If the team is team 1, return team 2, otherwise return team 1.
     *
     * @param team The team to get the opposite of.
     * @return The opposite team of the team passed in.
     */
    public static TeamEnum getOppositeTeam(TeamEnum team)
    {
        if (team.equals(TeamEnum.TEAM1))
            return TeamEnum.TEAM2;
        else
            return TeamEnum.TEAM1;
    }

}
