package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.Duel;
import dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA.PartyFFA;
import dev.nandi0813.practice.Manager.Match.MatchType.PartySplit.PartySplit;
import dev.nandi0813.practice.Manager.Match.Util.KitUtil;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class RoundManager
{

    private final Match match;
    @Getter private final HashMap<TeamEnum, Integer> wonRoundsTeam = new HashMap<>();
    @Getter private final HashMap<Player, Integer> wonRoundsPlayer = new HashMap<>();

    public RoundManager(Match match)
    {
        this.match = match;
    }

    /**
     * It sets the match status to START, starts the start countdown, sets the dropped items, sets the match player,
     * teleports the player, loads the kit, sets the hit delay, and adds the player to the alive players list
     */
    public void startRound()
    {
        match.setStatus(MatchStatus.START);
        match.getStartCountdown().begin();
        match.setDroppedItems(new HashSet<>());

        for (Player player : match.getPlayers())
        {
            // Set players match attributions
            Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () -> PlayerUtil.setMatchPlayer(player), 5L);

            // Teleport player
            PlayerUtil.teleportPlayer(player, match);

            // Custom kit
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            for (Player matchPlayer : match.getPlayers())
                if (!matchPlayer.equals(player))
                    player.showPlayer(matchPlayer);

            if (profile.getCustomKits().get(match.getLadder()) == null || !player.hasPermission("zonepractice.customkit"))
                KitUtil.loadKit(player, match.getLadder());
            else
            {
                player.getInventory().setItem(player.getInventory().firstEmpty(), ItemUtil.createItem("&a&lCustom Kit ", Material.ENCHANTED_BOOK));
                player.getInventory().setItem(8, KitUtil.getDefaultKitItem());
            }

            player.updateInventory();

            // Hit delay
            player.setMaximumNoDamageTicks(match.getLadder().getHitDelay());

            if (!match.getType().equals(MatchType.DUEL))
                match.getAlivePlayers().add(player);
        }
    }

    /**
     * It ends the round and starts the next one
     *
     * @param winner The player who won the round.
     */
    public void endRound(Player winner)
    {
        match.getDurationCountdown().cancel();
        match.getAlivePlayers().clear();

        endMatch(winner);
    }

    /**
     * It ends the match
     *
     * @param winner The player who won the match.
     */
    public void endMatch(Player winner)
    {
        switch (match.getType())
        {
            case DUEL: Duel.endMatch(match, winner, Duel.getOppositePlayer(match, winner)); break;
            case PARTY_FFA: PartyFFA.endMatch(match, winner); break;
            case PARTY_SPLIT: PartySplit.endMatch(match, winner); break;
        }

        match.setStatus(MatchStatus.OLD);
        if (!match.getAfterCountdown().isRunning())
            match.getAfterCountdown().begin();
    }

}
