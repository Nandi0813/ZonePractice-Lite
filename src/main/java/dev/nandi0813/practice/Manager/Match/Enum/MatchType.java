package dev.nandi0813.practice.Manager.Match.Enum;

import lombok.Getter;

public enum MatchType
{

    DUEL("&e&lDuel"),
    PARTY_FFA("&e&lParty FFA"),
    PARTY_SPLIT("&e&lParty Split");

    @Getter private final String name;

    MatchType(String name)
    {
        this.name = name;
    }

}
