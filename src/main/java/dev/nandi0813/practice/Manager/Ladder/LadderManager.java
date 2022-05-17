package dev.nandi0813.practice.Manager.Ladder;

import dev.nandi0813.practice.Manager.File.BackendManager;
import dev.nandi0813.practice.Manager.Ladder.Ladders.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LadderManager
{

    @Getter private final List<Ladder> ladders = new ArrayList<>();
    @Getter private final List<String> disabledLadders = new ArrayList<>();

    /**
     * Return the ladder with the given name, or null if no ladder with that name exists.
     *
     * @param ladderName The name of the ladder you want to get.
     * @return A ladder object
     */
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

    /**
     * It loads all the ladders into the ladders list
     */
    public void loadLadders()
    {
        ladders.add(new Axe(("Axe")));
        ladders.add(new BuildUHC(("BuildUHC")));
        ladders.add(new Combo(("Combo")));
        ladders.add(new Debuff(("Debuff")));
        ladders.add(new Gapple(("Gapple")));
        ladders.add(new NoDebuff(("NoDebuff")));
        ladders.add(new Soup(("Soup")));

        if (BackendManager.getConfig().getStringList("disabled-ladders") != null)
        {
            for (String ladderName : BackendManager.getConfig().getStringList("disabled-ladders"))
            {
                Ladder ladder = getLadder(ladderName);

                if (ladder != null)
                {
                    ladder.setEnabled(false);
                    disabledLadders.add(ladder.getName());
                }
            }
        }
    }

}
