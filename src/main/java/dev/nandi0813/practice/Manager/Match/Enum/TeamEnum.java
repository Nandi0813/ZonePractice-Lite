package dev.nandi0813.practice.Manager.Match.Enum;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import lombok.Getter;

public enum TeamEnum
{

    TEAM1 (LanguageManager.getString("match.teams.team1")),
    TEAM2 (LanguageManager.getString("match.teams.team2"));

    @Getter private final String name;

    TeamEnum(String name)
    {
        this.name = name;
    }

}
