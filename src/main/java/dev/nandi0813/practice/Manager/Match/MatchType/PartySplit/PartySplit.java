package dev.nandi0813.practice.Manager.Match.MatchType.PartySplit;

import dev.nandi0813.practice.Manager.File.LanguageManager;
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

            for (String line : LanguageManager.getList("match.partysplit.match-end"))
            {
                match.sendMessage(line
                                .replaceAll("%winnerTeam%", match.getTeams().get(winner).getName())
                                .replaceAll("%winnerPlayers%", winners.toString().replace("[", "").replace("]", ""))
                                .replaceAll("%loserTeam%", TeamUtil.getOppositeTeam(match.getTeams().get(winner)).getName())
                                .replaceAll("%loserPlayers%", losers.toString().replace("[", "").replace("]", ""))
                        , true);
            }
        }
        else
        {
            for (String line : LanguageManager.getList("match.partysplit.match-end-draw"))
                match.sendMessage(line, true);
        }
    }

    public static void killPlayer(Match match, Player player, boolean message)
    {
        match.getAlivePlayers().remove(player);
        List<Player> playerTeam = getTeamAlivePlayers(match,  match.getTeams().get(player));

        if (message)
            match.sendMessage(LanguageManager.getString("match.partysplit.player-die").replaceAll("%player%", player.getName()).replaceAll("%playerTeam%", match.getTeams().get(player).getName()).replaceAll("%playerTeamLeft%", String.valueOf(playerTeam.size())), true);

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
