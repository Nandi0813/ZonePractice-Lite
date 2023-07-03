package dev.nandi0813.practice.Manager.Ladder;

import dev.nandi0813.practice.Manager.File.LadderFile;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class LadderManager
{

    @Getter private final List<Ladder> ladders = new ArrayList<>();

    /**
     * Return the ladder with the given name, or null if no ladder with that name exists.
     *
     * @param ladderName The name of the ladder you want to get.
     * @return A ladder object
     */
    public Ladder getLadder(String ladderName)
    {
        for (Ladder ladder : ladders)
            if (ladder.getName() != null && ladder.getName().equalsIgnoreCase(ladderName))
                return ladder;
        return null;
    }

    public Ladder getLadder(int id)
    {
        for (Ladder ladder : ladders)
            if (ladder.getId() == id)
                return ladder;
        return null;
    }

    /**
     * Loads all the ladders into the ladders list
     */
    public void loadLadders()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
        {
            for (int id = 1; id < 10; id++)
                ladders.add(new Ladder(id));
        });
    }

    public void saveLadders()
    {
        for (Ladder ladder : ladders)
            ladder.saveData(false);

        LadderFile.save();
    }

}
