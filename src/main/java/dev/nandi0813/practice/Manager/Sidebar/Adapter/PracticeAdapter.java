package dev.nandi0813.practice.Manager.Sidebar.Adapter;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.Duel;
import dev.nandi0813.practice.Manager.Match.MatchType.PartySplit.PartySplit;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Queue.Queue;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import dev.nandi0813.practice.Util.TPSUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PracticeAdapter implements SidebarAdapter
{

    @Override
    public String getTitle(Player player)
    {
        return LanguageManager.getString("sidebar.title");
    }

    @Override
    public List<String> getLines(Player player)
    {
        List<String> sidebar = new ArrayList<>();
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if (profile.getStatus().equals(ProfileStatus.LOBBY) || profile.getStatus().equals(ProfileStatus.EDITOR))
        {
            Party party = SystemManager.getPartyManager().getParty(player);

            if (party == null)
            {
                sidebar.add("&7&m---------------------");
                sidebar.add("&fOnline: &6" + Bukkit.getOnlinePlayers().size());
                sidebar.add("&fIn Fights: &6" + SystemManager.getMatchManager().getMatchSize());
                sidebar.add("&fIn Queue: &6" + SystemManager.getQueueManager().getQueues().size());
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
            else
            {
                sidebar.add("&7&m---------------------");
                sidebar.add("&fOnline: &6" + Bukkit.getOnlinePlayers().size());
                sidebar.add("&fIn Fights: &6" + SystemManager.getMatchManager().getMatchSize());
                sidebar.add("&fIn Queue: &6" + SystemManager.getQueueManager().getQueues().size());
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
                sidebar.add("&fParty Leader: &6" + party.getLeader().getName());
                sidebar.add("&fParty Members: &6" + party.getMaxPlayerLimit() + "&7/&e" + party.getMembers().size());
                sidebar.add("&7&m---------------------");
            }
        }
        else if (profile.getStatus().equals(ProfileStatus.QUEUE))
        {
            Queue queue = SystemManager.getQueueManager().getQueue(player);
            if (queue == null) return null;

            sidebar.add("&7&m---------------------");
            sidebar.add("&fOnline: &6" + Bukkit.getOnlinePlayers().size());
            sidebar.add("&fIn Fights: &6" + SystemManager.getMatchManager().getMatchSize());
            sidebar.add("&fIn Queue: &6" + SystemManager.getQueueManager().getQueues().size());
            sidebar.add("&7&m---------------------");
            sidebar.add((queue.isRanked() ? "&cRanked" : "&aUnranked") + " " + queue.getLadder().getIcon().getItemMeta().getDisplayName());
            sidebar.add("&fTime: &6" + StringUtil.formatMillisecondsToMinutes(queue.getQueueRunnable().getSeconds() * 1000L));
            sidebar.add("");
            sidebar.add(LanguageManager.getString("sidebar.ip-line"));
            sidebar.add("&7&m---------------------");
        }
        else if (profile.getStatus().equals(ProfileStatus.MATCH))
        {
            Match match = SystemManager.getMatchManager().getLiveMatchByPlayer(player);
            if (match == null) return null;

            if (match.getType().equals(MatchType.DUEL))
            {
                OfflinePlayer enemy = Duel.getOppositePlayer(match, player);
                String enemyPing;
                if (enemy.isOnline())
                    enemyPing = PlayerUtil.getPing(enemy.getPlayer()) + "ms";
                else
                    enemyPing = "N/A";

                sidebar.add("&7&m---------------------");
                sidebar.add("&6Fighting: &f" + enemy.getName());
                sidebar.add("");
                sidebar.add("&aYour Ping: &f" + PlayerUtil.getPing(player) + "ms");
                sidebar.add("&cTheir Ping: &f" + enemyPing);
                sidebar.add("");
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
            else if (match.getType().equals(MatchType.PARTY_FFA))
            {
                sidebar.add("&7&m---------------------");
                sidebar.add("&6&lParty FFA");
                sidebar.add("");
                sidebar.add("&fPlayers: &6" + match.getPlayers().size() + "&7/&e" + match.getAlivePlayers().size());
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
            else if (match.getType().equals(MatchType.PARTY_SPLIT))
            {
                List<Player> team1 = PartySplit.getTeamPlayers(match, TeamEnum.TEAM1);
                List<Player> team2 = PartySplit.getTeamPlayers(match, TeamEnum.TEAM2);

                sidebar.add("&7&m---------------------");
                sidebar.add("&6&lParty Split");
                sidebar.add("");
                sidebar.add(TeamEnum.TEAM1.getName() + "&7: &9" + team1.size() + "&7/&9" + PartySplit.getTeamAlivePlayers(match, TeamEnum.TEAM1).size());
                sidebar.add(TeamEnum.TEAM2.getName() + "&7: &c" + team2.size() + "&7/&c" + PartySplit.getTeamAlivePlayers(match, TeamEnum.TEAM2).size());
                sidebar.add("");
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
        }
        else if (profile.getStatus().equals(ProfileStatus.SPECTATE))
        {
            Match match = SystemManager.getMatchManager().getLiveMatchBySpectator(player);
            if (match == null) return null;

            if (match.getType().equals(MatchType.DUEL))
            {
                Player player1 = match.getPlayers().get(0);
                String player1Ping;
                if (player1 != null) player1Ping = PlayerUtil.getPing(player1) + "ms";
                else player1Ping = "N/A";

                Player player2 = match.getPlayers().get(1);
                String player2Ping;
                if (player2 != null) player2Ping = PlayerUtil.getPing(player2) + "ms";
                else player2Ping = "N/A";

                sidebar.add("&7&m---------------------");
                sidebar.add("&aKit: " + match.getLadder().getIcon().getItemMeta().getDisplayName());
                sidebar.add("");
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add("&e" + player1.getName() + "'s Ping: &f" + player1Ping);
                sidebar.add("&e" + player2.getName() + "'s Ping: &f" + player2Ping);
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
            else if (match.getType().equals(MatchType.PARTY_FFA))
            {
                sidebar.add("&7&m---------------------");
                sidebar.add("&aKit: " + match.getLadder().getIcon().getItemMeta().getDisplayName());
                sidebar.add("");
                sidebar.add("&6&lParty FFA");
                sidebar.add("&fLeader: &e" + SystemManager.getPartyManager().getParty(match).getLeader().getName());
                sidebar.add("&fAlive: &6" + match.getPlayers().size() + "&7/&e" + match.getAlivePlayers().size());
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
            else if (match.getType().equals(MatchType.PARTY_SPLIT))
            {
                List<Player> team1 = PartySplit.getTeamPlayers(match, TeamEnum.TEAM1);
                List<Player> team2 = PartySplit.getTeamPlayers(match, TeamEnum.TEAM2);

                sidebar.add("&7&m---------------------");
                sidebar.add("&aKit: " + match.getLadder().getIcon().getItemMeta().getDisplayName());
                sidebar.add("");
                sidebar.add("&6&lParty Split");
                sidebar.add(TeamEnum.TEAM1.getName() + "&7: &9" + team1.size() + "&7/&9" + PartySplit.getTeamAlivePlayers(match, TeamEnum.TEAM1).size());
                sidebar.add(TeamEnum.TEAM2.getName() + "&7: &c" + team2.size() + "&7/&c" + PartySplit.getTeamAlivePlayers(match, TeamEnum.TEAM2).size());
                sidebar.add("");
                sidebar.add("&fDuration: &6" + StringUtil.formatMillisecondsToMinutes(match.getDurationCountdown().getSeconds() * 1000L));
                sidebar.add("");
                sidebar.add(LanguageManager.getString("sidebar.ip-line"));
                sidebar.add("&7&m---------------------");
            }
        }
        /*
        else if (profile.getStatus().equals(ProfileStatus.STAFFMODE))
        {

        }
        else if (profile.getStatus().equals(ProfileStatus.EVENT))
        {

        }
         */

        if (player.hasPermission("zonepractice.admin"))
        {
            sidebar.add("&fTPS: &6" + TPSUtil.get1MinTPSRounded());
            sidebar.add("&7&m---------------------");
        }

        return StringUtil.CC(sidebar);
    }

}
