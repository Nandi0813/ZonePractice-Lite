package dev.nandi0813.practice.Manager.Match.Enum;

import lombok.Getter;

public enum TeamEnum
{

    TEAM1 ("&9Team 1"),
    TEAM2 ("&cTeam 2");

    @Getter private final String name;

    TeamEnum(String name)
    {
        this.name = name;
    }

}
