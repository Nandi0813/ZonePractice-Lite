package dev.nandi0813.practice.Manager.Match.MatchType.Duel;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Duel
{

    public static void startMatch(Match match)
    {
        Player player1 = match.getPlayers().get(0);
        Player player2 = match.getPlayers().get(1);

        match.getTeams().put(player1, TeamEnum.TEAM1);
        match.getTeams().put(player2, TeamEnum.TEAM2);
    }

    public static void endMatch(Match match, Player winner, Player loser)
    {
        if (winner != null && loser != null)
        {
            ArrayList<Player> players = new ArrayList<>();
            players.addAll(match.getPlayers());
            players.addAll(match.getSpectators());

            match.sendMessage("&7&m----------------------------------------", true);
            match.sendMessage("&6Post-Match Inventories &7(click name to view)", true);
            for (Player player : players)
                player.spigot().sendMessage(ClickableText.getMessager(match.getMatchID(), winner, loser));
            match.sendMessage("&7&m----------------------------------------", true);

            if (match.isRanked())
            {
                int elochange = ConfigManager.getInt("ranked.elo-change");

                Profile winnerProfile = SystemManager.getProfileManager().getProfiles().get(winner);
                int winnerOldElo = winnerProfile.getElo().get(match.getLadder());
                int winnerNewElo = winnerOldElo + elochange;
                winnerProfile.getElo().put(match.getLadder(), winnerNewElo);

                Profile loserProfile = SystemManager.getProfileManager().getProfiles().get(loser);
                int loserOldElo = loserProfile.getElo().get(match.getLadder());
                int loserNewElo = loserOldElo - elochange;
                loserProfile.getElo().put(match.getLadder(), loserNewElo);

                match.sendMessage("&eElo Changes: &a" + winnerProfile.getPlayer().getName() + " +" + elochange + " (" + winnerNewElo + ") &c" + loserProfile.getPlayer().getName() + " -" + elochange + " (" + loserNewElo + ")", true);
                match.sendMessage("&7&m----------------------------------------", true);
            }
        }
        else
        {
            match.sendMessage("&7&m----------------------------------------", true);
            match.sendMessage("&cThe match is ended with no winner or loser.", true);
            match.sendMessage("&7&m----------------------------------------", true);
        }
    }

    public static void killPlayer(Match match, Player player, boolean teleport)
    {
        if (teleport)
            PlayerUtil.teleportPlayer(player, match);
        PlayerUtil.setMatchPlayer(player);

        for (Player matchPlayer : match.getPlayers())
        {
            if (!matchPlayer.equals(player))
            {
                PlayerUtil.dropPlayerInventory(player, match);
                match.getRoundManager().endRound(matchPlayer);
            }
        }
    }

    public static Player getOppositePlayer(Match match, Player player)
    {
        for (Player matchPlayer : match.getPlayers())
            if (!matchPlayer.equals(player))
                return matchPlayer;
        return null;
    }

}
