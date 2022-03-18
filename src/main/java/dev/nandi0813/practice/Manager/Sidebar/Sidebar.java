package dev.nandi0813.practice.Manager.Sidebar;

import dev.nandi0813.practice.Manager.Sidebar.Adapter.PracticeAdapter;
import dev.nandi0813.practice.Manager.Sidebar.Adapter.SidebarAdapter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Sidebar
{

    @Getter private final Player player;
    @Getter private final Scoreboard scoreboard;
    @Getter private final Objective objective;
    @Getter private final SidebarAdapter sidebarAdapter;
    @Getter private int count;

    public Sidebar(Player player)
    {
        this.player = player;
        this.sidebarAdapter = new PracticeAdapter();
        this.scoreboard = getOrCreateScoreboard();
        this.objective = getOrCreateObjective();
        this.count = -1;
    }

    public void update()
    {
        if (!objective.getDisplayName().equals(sidebarAdapter.getTitle(player)))
            objective.setDisplayName(sidebarAdapter.getTitle(player));

        if (!sidebarAdapter.getLines(player).isEmpty())
        {
            player.setScoreboard(scoreboard);

            if (sidebarAdapter.getLines(player).size() > 15)
            {
                sidebarAdapter.getLines(player).clear();
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                return;
            }

            for (int i = 0; i < sidebarAdapter.getLines(player).size(); i++)
            {
                String line = sidebarAdapter.getLines(player).get(i);
                Team team = getOrCreateTeam(i);
                String prefix, suffix;

                if (line.length() > 16)
                {
                    prefix = line.substring(0, 16);

                    if (prefix.charAt(15) == ChatColor.COLOR_CHAR)
                    {
                        prefix = prefix.substring(0, 15);
                        suffix = line.substring(15);
                    }
                    else if (prefix.charAt(14) == ChatColor.COLOR_CHAR)
                    {
                        prefix = prefix.substring(0, 14);
                        suffix = line.substring(14);
                    }
                    else
                    {
                        suffix = ChatColor.getLastColors(prefix) + line.substring(16);
                    }

                    if (suffix.length() > 16)
                    {
                        suffix.substring(0, 16);
                    }

                }
                else
                {
                    prefix = line;
                    suffix = "";
                }
                team.setPrefix(prefix);
                team.setSuffix(suffix);

                objective.getScore(getNameIndex(i)).setScore(sidebarAdapter.getLines(player).size() - i);
            }

            for (int i = 0; i < count - sidebarAdapter.getLines(player).size(); ++i)
            {
                remove(sidebarAdapter.getLines(player).size() + i);
            }

            count = sidebarAdapter.getLines(player).size();
            sidebarAdapter.getLines(player).clear();
        }
        else
        {
            sidebarAdapter.getLines(player).clear();
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }


    public Scoreboard getOrCreateScoreboard()
    {
        return Bukkit.getServer().getScoreboardManager().getNewScoreboard();
    }

    public Objective getOrCreateObjective()
    {
        Objective objective = scoreboard.getObjective("sidebar");

        if (objective == null)
            objective = scoreboard.registerNewObjective("sidebar", "dummy");

        objective.setDisplayName("Loading...");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return objective;
    }

    public Team getOrCreateTeam(int teamindex)
    {
        Team team = scoreboard.getTeam("line-" + teamindex);

        if (team == null)
            team = scoreboard.registerNewTeam("line-" + teamindex);

        if (!team.hasEntry(getNameIndex(teamindex)))
            team.addEntry(getNameIndex(teamindex));

        return team;
    }

    public void remove(int index)
    {
        scoreboard.resetScores(ChatColor.values()[index].toString() + ChatColor.RESET);
        Team team = getOrCreateTeam(index);
        team.unregister();
    }

    public String getNameIndex(int index)
    {
        return ChatColor.values()[index].toString() + ChatColor.RESET;
    }

}
