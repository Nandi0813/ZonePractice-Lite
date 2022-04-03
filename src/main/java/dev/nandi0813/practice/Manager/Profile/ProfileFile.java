package dev.nandi0813.practice.Manager.Profile;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class ProfileFile
{

    private final Profile profile;
    private final File file;
    private final YamlConfiguration config;

    public ProfileFile(Profile profile)
    {
        this.profile = profile;
        file = new File(Practice.getInstance().getDataFolder() + "/profiles", profile.getUuid().toString().toLowerCase() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void setProfileData()
    {
        config.set("uuid", profile.getUuid().toString());

        // Elo
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isRanked())
                config.set("stats.elo." + ladder.getName(), profile.getElo().get(ladder));
        }

        // Summarized wins/losses
        config.set("stats.unranked.wins", profile.getUnrankedWins());
        config.set("stats.unranked.losses", profile.getUnrankedLosses());
        config.set("stats.ranked.wins", profile.getRankedWins());
        config.set("stats.ranked.losses", profile.getRankedLosses());

        // Ladder win/lose stats
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (profile.getLadderUnRankedWins().get(ladder) != null)
                config.set("stats.ladder-stats." + ladder.getName() + ".unranked.wins", profile.getLadderUnRankedWins().get(ladder));
            else
                config.set("stats.ladder-stats." + ladder.getName() + ".unranked.wins", 0);

            if (profile.getLadderUnRankedLosses().get(ladder) != null)
                config.set("stats.ladder-stats." + ladder.getName() + ".unranked.losses", profile.getLadderUnRankedLosses().get(ladder));
            else
                config.set("stats.ladder-stats." + ladder.getName() + ".unranked.losses", 0);

            if (ladder.isRanked())
            {
                if (profile.getLadderRankedWins().get(ladder) != null)
                    config.set("stats.ladder-stats." + ladder.getName() + ".ranked.wins", profile.getLadderRankedWins().get(ladder));
                else
                    config.set("stats.ladder-stats." + ladder.getName() + ".ranked.wins", 0);

                if (profile.getLadderRankedLosses().get(ladder) != null)
                    config.set("stats.ladder-stats." + ladder.getName() + ".ranked.losses", profile.getLadderRankedLosses().get(ladder));
                else
                    config.set("stats.ladder-stats." + ladder.getName() + ".ranked.losses", 0);
            }
        }

        // Custom kits
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (profile.getCustomKits().get(ladder) != null)
                config.set("customkit." + ladder.getName().toLowerCase() + ".inventory", profile.getCustomKits().get(ladder));
        }

        saveFile();
    }

    public void setDefaultData()
    {
        config.set("settings.duelrequest", true);
        config.set("settings.sidebar", true);
        config.set("settings.hideplayers", false);
        config.set("settings.partyinvites", true);
        config.set("settings.allowspectate", true);
        config.set("settings.messages", true);

        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (ladder.isRanked())
                config.set("stats.elo." + ladder.getName(), 1000);
        }

        config.set("stats.unranked.wins", 0);
        config.set("stats.unranked.losses", 0);
        config.set("stats.ranked.wins", 0);
        config.set("stats.ranked.losses", 0);

        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            config.set("stats.ladder-stats." + ladder.getName() + ".unranked.wins", 0);
            config.set("stats.ladder-stats." + ladder.getName() + ".unranked.losses", 0);

            if (ladder.isRanked())
            {
                config.set("stats.ladder-stats." + ladder.getName() + ".ranked.wins", 0);
                config.set("stats.ladder-stats." + ladder.getName() + ".ranked.losses", 0);
            }
        }

        saveFile();
    }

    public void getProfileData()
    {
        for (String ladderName : config.getConfigurationSection("stats.elo").getKeys(false))
        {
            Ladder ladder = SystemManager.getLadderManager().getLadder(ladderName);
            if (ladder != null)
                profile.getElo().put(ladder, config.getInt("stats.elo." + ladder.getName()));
        }

        profile.setUnrankedWins(config.getInt("stats.unranked.wins"));
        profile.setUnrankedLosses(config.getInt("stats.unranked.losses"));
        profile.setRankedWins(config.getInt("stats.ranked.wins"));
        profile.setRankedLosses(config.getInt("stats.ranked.losses"));

        for (String ladderName : config.getConfigurationSection("stats.ladder-stats").getKeys(false))
        {
            Ladder ladder = SystemManager.getLadderManager().getLadder(ladderName);
            if (ladder != null)
            {
                profile.getLadderUnRankedWins().put(ladder, config.getInt("stats.ladder-stats." + ladder.getName() + ".unranked.wins"));
                profile.getLadderUnRankedLosses().put(ladder, config.getInt("stats.ladder-stats." + ladder.getName() + ".unranked.losses"));

                if (ladder.isRanked())
                {
                    if (config.isSet("stats.ladder-stats." + ladder.getName() + ".ranked.wins"))
                        profile.getLadderRankedWins().put(ladder, config.getInt("stats.ladder-stats." + ladder.getName() + ".ranked.wins"));
                    if (config.isSet("stats.ladder-stats." + ladder.getName() + ".ranked.losses"))
                        profile.getLadderRankedLosses().put(ladder, config.getInt("stats.ladder-stats." + ladder.getName() + ".ranked.losses"));
                }
            }
        }

        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            if (config.isList("customkit." + ladder.getName().toLowerCase() + ".inventory"))
            {
                ItemStack[] inventory = config.getList("customkit." + ladder.getName().toLowerCase() + ".inventory").toArray(new ItemStack[0]);
                profile.getCustomKits().put(ladder, inventory);
            }
        }
    }

    public void deleteCustomKit(Ladder ladder)
    {
        config.set("customkit." + ladder.getName().toLowerCase(), null);
        saveFile();
    }

    public void saveFile()
    {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
