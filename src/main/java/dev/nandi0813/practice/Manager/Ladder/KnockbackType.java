package dev.nandi0813.practice.Manager.Ladder;

import lombok.Getter;

public enum KnockbackType
{

    DEFAULT("Default"),
    COMBO("Combo");

    @Getter private final String name;

    KnockbackType(String name)
    {
        this.name = name;
    }

}
