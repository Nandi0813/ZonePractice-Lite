package dev.nandi0813.practice.Command.Leaderboard;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class LeaderboardCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            Profile profile = Practice.getProfileManager().getProfiles().get(player);

            if (!profile.getStatus().equals(ProfileStatus.OFFLINE))
            {
                if (args.length == 2 && (args[1].equalsIgnoreCase("elo") || args[1].equalsIgnoreCase("win")))
                {
                    Ladder ladder = Practice.getLadderManager().getLadder(args[0]);

                    if (ladder != null)
                    {
                        if (ladder.isEnabled())
                        {
                            if (args[1].equalsIgnoreCase("elo"))
                            {
                                createLeaderboard(ladder, "elo", result ->
                                {
                                    ArrayList<OfflinePlayer> topPlayers = new ArrayList<>();
                                    for (OfflinePlayer p : result.keySet())
                                    {
                                        if (topPlayers.size() <= ConfigManager.getInt("show-player"))
                                            topPlayers.add(p);
                                        else
                                            break;
                                    }

                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                    player.sendMessage(LanguageManager.getString("leaderboard.elo.title").replaceAll("%ladder%", ladder.getName()));
                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                    for (int i = 0; i < ConfigManager.getInt("show-player"); i++)
                                    {
                                        if (topPlayers.size() > i)
                                        {
                                            OfflinePlayer target = topPlayers.get(i);
                                            player.sendMessage(LanguageManager.getString("leaderboard.elo.place-line")
                                                    .replaceAll("%place%", String.valueOf((i+1)))
                                                    .replaceAll("%player%", target.getName())
                                                    .replaceAll("%elo%", String.valueOf(result.get(target))));
                                        }
                                    }
                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                });
                            }
                            else
                            {
                                createLeaderboard(ladder, "win", result ->
                                {
                                    ArrayList<OfflinePlayer> topPlayers = new ArrayList<>();
                                    for (OfflinePlayer p : result.keySet())
                                    {
                                        if (topPlayers.size() <= ConfigManager.getInt("show-player"))
                                            topPlayers.add(p);
                                        else
                                            break;
                                    }

                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                    player.sendMessage(LanguageManager.getString("leaderboard.win.title").replaceAll("%ladder%", ladder.getName()));
                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                    for (int i = 0; i < ConfigManager.getInt("show-player"); i++)
                                    {
                                        if (topPlayers.size() > i)
                                        {
                                            OfflinePlayer target = topPlayers.get(i);
                                            player.sendMessage(LanguageManager.getString("leaderboard.win.place-line")
                                                    .replaceAll("%place%", String.valueOf((i+1)))
                                                    .replaceAll("%player%", target.getName())
                                                    .replaceAll("%wins%", String.valueOf(result.get(target))));
                                        }
                                    }
                                    player.sendMessage(StringUtil.CC("&7&m------------------------------------"));
                                });
                            }
                        }
                        else
                            player.sendMessage(LanguageManager.getString("leaderboard.ladder-disabled"));
                    }
                    else
                        player.sendMessage(LanguageManager.getString("leaderboard.ladder-not-found"));
                }
                else
                    player.sendMessage(StringUtil.CC("&c/" + label + " <ladder> <elo/win>"));
            }
        }
        return true;
    }


    public static void createLeaderboard(final Ladder ladder, final String engine, final LeaderboardCallback callback)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
        {
            HashMap<OfflinePlayer, Integer> leaderboard = new HashMap<>();

            for (Profile profile : Practice.getProfileManager().getProfiles().values())
            {
                if (engine.equalsIgnoreCase("elo"))
                {
                    int elo = profile.getElo().get(ladder);
                    leaderboard.put(profile.getPlayer(), elo);
                }
                else
                {
                    int unrankedWin = profile.getLadderUnRankedWins().get(ladder);
                    int rankedWin = profile.getLadderRankedWins().get(ladder);

                    leaderboard.put(profile.getPlayer(), (unrankedWin + rankedWin));
                }
            }

            HashMap<OfflinePlayer, Integer> sortedLeaderboard = sortByValue(leaderboard);

            Bukkit.getScheduler().runTask(Practice.getInstance(), () -> callback.onQueryDone(sortedLeaderboard));
        });
    }

    public static HashMap<OfflinePlayer, Integer> sortByValue(HashMap<OfflinePlayer, Integer> map)
    {
        LinkedHashMap<OfflinePlayer, Integer> reverseSortedMap = new LinkedHashMap<>();

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

}
