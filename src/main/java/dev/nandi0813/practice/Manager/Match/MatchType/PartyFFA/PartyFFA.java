package dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA;

import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.*;

public class PartyFFA
{

    public static void startMatch(Match match)
    {
        for (Player player : match.getPlayers())
            match.getTeams().put(player, TeamEnum.TEAM1);
    }

    public static void endMatch(Match match, Player winner)
    {
        if (winner != null)
        {
            ArrayList<String> losers = new ArrayList<>();
            for (Player player : match.getPlayers())
                if (!player.equals(winner)) losers.add(player.getName());

            match.sendMessage("&7&m----------------------------------------", true);
            match.sendMessage("&aWinner: &e" + winner.getName(), true);
            match.sendMessage("&cLosers: &e" + losers.toString().replace("[", "").replace("]", ""), true);
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
        if (message)
            match.sendMessage("&c" + player.getName() + " died.", true);
        match.getAlivePlayers().remove(player);

        if (player.isOnline())
            PlayerUtil.hidePlayerPartyGames(match, player);

        if (match.getAlivePlayers().size() == 1)
        {
            match.getRoundManager().endRound(match.getAlivePlayers().stream().findAny().get());
        }
    }

}
