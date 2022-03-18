package dev.nandi0813.practice.Manager.Match.Util;

import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;

public class TeamUtil
{

    public static TeamEnum getOppositeTeam(TeamEnum team)
    {
        if (team.equals(TeamEnum.TEAM1))
            return TeamEnum.TEAM2;
        else
            return TeamEnum.TEAM1;
    }

}
