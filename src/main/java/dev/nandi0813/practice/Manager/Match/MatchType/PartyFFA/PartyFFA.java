package dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA;

import dev.nandi0813.practice.Manager.File.LanguageManager;
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

            for (String line : LanguageManager.getList("match.partyffa.match-end"))
                match.sendMessage(line.replaceAll("%winner%", winner.getName()).replaceAll("%losers%", losers.toString().replace("[", "").replace("]", "")), true);
        }
        else
        {
            for (String line : LanguageManager.getList("match.partyffa.match-end-draw"))
                match.sendMessage(line, true);
        }
    }

    public static void killPlayer(Match match, Player player, boolean message)
    {
        if (message)
            match.sendMessage(LanguageManager.getString("match.partyffa.player-die").replaceAll("%player%", player.getName()), true);
        match.getAlivePlayers().remove(player);

        if (player.isOnline())
            PlayerUtil.hidePlayerPartyGames(match, player);

        if (match.getAlivePlayers().size() == 1)
        {
            match.getRoundManager().endRound(match.getAlivePlayers().stream().findAny().get());
        }
    }

}
