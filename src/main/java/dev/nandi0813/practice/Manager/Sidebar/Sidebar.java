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

    /**
     * If the title of the sidebar has changed, update it. If the lines of the sidebar have changed, update them. If the
     * lines of the sidebar are empty, clear the sidebar
     */
    public void update()
    {
        if (!objective.getDisplayName().equals(sidebarAdapter.getTitle(player)))
            objective.setDisplayName(sidebarAdapter.getTitle(player));

        if (sidebarAdapter.getLines(player) != null && !sidebarAdapter.getLines(player).isEmpty())
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
                        suffix = suffix.substring(0, 16);
                    }
                    if ((prefix + suffix).length() > 32)
                    {
                        suffix = "";
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
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }


    /**
     * If the player has a scoreboard, return it. If not, create one and return it
     *
     * @return A new scoreboard.
     */
    public Scoreboard getOrCreateScoreboard()
    {
        return Bukkit.getServer().getScoreboardManager().getNewScoreboard();
    }

    /**
     * If the objective doesn't exist, create it. If it does exist, return it
     *
     * @return The Objective
     */
    public Objective getOrCreateObjective()
    {
        Objective objective = scoreboard.getObjective("sidebar");

        if (objective == null)
            objective = scoreboard.registerNewObjective("sidebar", "dummy");

        objective.setDisplayName("Loading...");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return objective;
    }

    /**
     * "Get the team for the given line index, or create it if it doesn't exist."
     *
     * The first thing we do is get the team for the given line index. If it doesn't exist, we create it
     *
     * @param teamindex The index of the team.
     * @return The team that is being returned is the team that is being created.
     */
    public Team getOrCreateTeam(int teamindex)
    {
        Team team = scoreboard.getTeam("line-" + teamindex);

        if (team == null)
            team = scoreboard.registerNewTeam("line-" + teamindex);

        if (!team.hasEntry(getNameIndex(teamindex)))
            team.addEntry(getNameIndex(teamindex));

        return team;
    }

    /**
     * It removes a line from the scoreboard
     *
     * @param index The index of the team to remove.
     */
    public void remove(int index)
    {
        scoreboard.resetScores(ChatColor.values()[index].toString() + ChatColor.RESET);
        Team team = getOrCreateTeam(index);
        team.unregister();
    }

    /**
     * It returns the name of the color at the given index
     *
     * @param index The index of the name in the list.
     * @return The name of the color at the given index.
     */
    public String getNameIndex(int index)
    {
        return ChatColor.values()[index].toString() + ChatColor.RESET;
    }

}
