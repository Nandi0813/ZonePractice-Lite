package dev.nandi0813.practice.Command.duel;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUIs.Match.LadderSelectorParty;
import dev.nandi0813.practice.Manager.Gui.GUIs.Match.LadderSelectorRequest;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuelCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            Profile profile = Practice.getProfileManager().getProfiles().get(player);

            if (args.length == 2) {
                if (!args[0].equalsIgnoreCase("accept")) {
                    sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.syntax")));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.player-not-found")));
                    return true;
                }

                Profile targetProfile = Practice.getProfileManager().getProfiles().get(target);

                if (targetProfile.getSendRequests().containsKey(player)) {
                    Ladder ladder = targetProfile.getSendRequests().get(player);
                    targetProfile.getSendRequests().remove(player);

                    Arena arena = Practice.getArenaManager().getRandomArena(ladder.isBuild());
                    if (arena != null)
                    {
                        Match match = new Match(MatchType.DUEL, Arrays.asList(player, target), ladder, false, arena);
                        match.startMatch();
                    } else {
                        player.sendMessage(LanguageManager.getString("party.no-available-arena"));
                        target.sendMessage(LanguageManager.getString("party.no-available-arena"));
                    }
                }

                return true;
            }
            else if (args.length != 1) {
                sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.syntax")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null)
            {
                sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.player-not-found")));
                return true;
            }
            else if (target == player)
            {
                sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.request-yourself")));
                return true;
            }
            else if (profile.getSendRequests().containsKey(target))
            {
                sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.request-already")));
                return true;
            }

            Party party = Practice.getPartyManager().getParty(player);

            if (party != null) {
                player.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.party")));
                return true;
            }

            if (profile.getStatus().equals(ProfileStatus.LOBBY) || profile.getStatus().equals(ProfileStatus.SPECTATE)) {
                Profile targetProfile = Practice.getProfileManager().getProfiles().get(target);

                if (targetProfile.getStatus().equals(ProfileStatus.MATCH)) {
                    sender.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.player-is-in-match")));
                    return true;
                }

                new LadderSelectorRequest(target).open(player);
            } else {
                player.sendMessage(StringUtil.CC(LanguageManager.getString("duel-command.in-match")));
            }
        } else {
            sender.sendMessage("You cant send duel to another player from console.");
        }

        return true;
    }

}
