package dev.nandi0813.practice.Manager.Match.MatchType.PartySplit;

import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import dev.nandi0813.practice.Manager.Match.Util.TeamUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartySplit
{

    public static void startMatch(Match match)
    {
        // Collect and shuffle players
        List<Player> players = new ArrayList<>(match.getPlayers());
        Collections.shuffle(players);

        // Split players to the teams.
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        for (Player player : players)
        {
            if (team2.size() > team1.size())
                team1.add(player);
            else
                team2.add(player);
        }

        // Add players to the teams.
        for (Player player : team1)
            match.getTeams().put(player, TeamEnum.TEAM1);
        for (Player player : team2)
            match.getTeams().put(player, TeamEnum.TEAM2);
    }

    public static void endMatch(Match match, Player winner)
    {
        if (winner != null)
        {
            ArrayList<String> winners = new ArrayList<>();
            ArrayList<String> losers = new ArrayList<>();
            for (Player player : match.getPlayers())
            {
                if (match.getTeams().get(player).equals(match.getTeams().get(winner)))
                    winners.add(player.getName());
                else
                    losers.add(player.getName());
            }

            match.sendMessage("&7&m----------------------------------------", true);
            match.sendMessage("&aWinner: &e" + match.getTeams().get(winner).getName() + " &7- &e" + winners.toString().replace("[", "").replace("]", ""), true);
            match.sendMessage("&cLoser: &e" + TeamUtil.getOppositeTeam(match.getTeams().get(winner)).getName() + " &7- &e" + losers.toString().replace("[", "").replace("]", ""), true);
            match.sendMessage("&7&m----------------------------------------", true);
        }
        else
        {
            match.sendMessage("&7&m----------------------------------------", true);
            match.sendMessage("&cThe match is ended with no winner.", true);
            match.sendMessage("&7&m----------------------------------------", true);
        }
    }

    public static void killPlayer(Match match, Player player, boolean message)
    {
        match.getAlivePlayers().remove(player);
        List<Player> playerTeam = getTeamAlivePlayers(match,  match.getTeams().get(player));

        if (message)
            match.sendMessage("&c" + player.getName() + " died. &7(" + match.getTeams().get(player).getName() + "&7) - &e" + playerTeam.size() + " &7players left in their team.", true);

        if (player.isOnline())
            PlayerUtil.hidePlayerPartyGames(match, player);

        if (playerTeam.isEmpty())
            match.getRoundManager().endRound(match.getAlivePlayers().stream().findAny().get());
    }


    public static List<Player> getTeamPlayers(Match match, TeamEnum team)
    {
        List<Player> players = new ArrayList<>();
        for (Player player : match.getPlayers())
            if (match.getTeams().get(player).equals(team))
                players.add(player);
        return players;
    }

    public static List<Player> getTeamAlivePlayers(Match match, TeamEnum team)
    {
        List<Player> players = new ArrayList<>();
        for (Player player : getTeamPlayers(match, team))
            if (match.getAlivePlayers().contains(player))
                players.add(player);
        return players;
    }

}
