package dev.nandi0813.practice.Manager.Ladder;

import dev.nandi0813.practice.Manager.Ladder.Ladders.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LadderManager
{

    @Getter private final List<Ladder> ladders = new ArrayList<>();

    public Ladder getLadder(String ladderName)
    {
        for (Ladder ladder : ladders)
        {
            if (ladder.getName().equalsIgnoreCase(ladderName))
            {
                return ladder;
            }
        }
        return null;
    }

    public Ladder getLadder(Ladder ladderID)
    {
        for (Ladder ladder : ladders)
        {
            if (ladder.equals(ladderID))
            {
                return ladder;
            }
        }
        return null;
    }

    public void loadLadders()
    {
        ladders.add(new Axe(("Axe")));
        ladders.add(new BuildUHC(("BuildUHC")));
        ladders.add(new Combo(("Combo")));
        ladders.add(new Debuff(("Debuff")));
        ladders.add(new Gapple(("Gapple")));
        ladders.add(new NoDebuff(("NoDebuff")));
        ladders.add(new Soup(("Soup")));
    }

}
