package dev.nandi0813.practice.Manager.Match;

import dev.nandi0813.practice.Event.MatchEndEvent;
import dev.nandi0813.practice.Event.MatchStartEvent;
import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Arena.BlockChange.CachedBlock;
import dev.nandi0813.practice.Manager.Arena.BlockChange.Rollback;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Enum.MatchStatus;
import dev.nandi0813.practice.Manager.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Match.MatchStats.PlayerMatchStat;
import dev.nandi0813.practice.Manager.Match.MatchType.Duel.Duel;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Match.MatchType.PartyFFA.PartyFFA;
import dev.nandi0813.practice.Manager.Match.MatchType.PartySplit.PartySplit;
import dev.nandi0813.practice.Manager.Match.Runnable.AfterCountdown;
import dev.nandi0813.practice.Manager.Match.Runnable.DurationCountdown;
import dev.nandi0813.practice.Manager.Match.Runnable.StartCountdown;
import dev.nandi0813.practice.Manager.Match.Util.PlayerUtil;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.*;

public class Match
{

    @Getter private final String matchID;
    @Getter private final RoundManager roundManager;
    @Getter private final MatchType type;
    @Getter @Setter private MatchStatus status;
    @Getter private final Ladder ladder;
    @Getter private final boolean ranked;

    @Getter private final Arena arena;
    @Getter @Setter private Arena gameArena;

    @Getter private final List<Player> players;
    @Getter private final List<Player> spectators = new ArrayList<>();
    @Getter private final HashMap<Player, TeamEnum> teams = new HashMap<>();
    // For team matches
    @Getter private final List<Player> alivePlayers = new ArrayList<>();

    @Getter private final HashSet<CachedBlock> blockChange;
    @Getter @Setter private HashSet<Item> droppedItems = new HashSet<>();
    @Getter private final HashMap<OfflinePlayer, PlayerMatchStat> matchStats = new HashMap<>();

    @Getter @Setter private StartCountdown startCountdown;
    @Getter @Setter private DurationCountdown durationCountdown;
    @Getter @Setter private AfterCountdown afterCountdown;

    public Match(MatchType matchType, List<Player> players, Ladder ladder, boolean ranked, Arena arena)
    {
        matchID = "match" + System.currentTimeMillis();
        this.type = matchType;
        this.players = players;
        this.ladder = ladder;
        this.ranked = ranked;
        this.arena = arena;
        blockChange = new HashSet<>();
        roundManager = new RoundManager(this);
        startCountdown = new StartCountdown(this);
        durationCountdown = new DurationCountdown();
        afterCountdown = new AfterCountdown(this);
    }

    public void startMatch()
    {
        MatchStartEvent matchStartEvent = new MatchStartEvent(this);
        Bukkit.getPluginManager().callEvent(matchStartEvent);

        if (matchStartEvent.isCancelled())
        {
            sendMessage("&cThe match got cancelled!", false);
            return;
        }

        if (!type.equals(MatchType.PARTY_FFA))
        {
            roundManager.getWonRoundsTeam().put(TeamEnum.TEAM1, 0);
            roundManager.getWonRoundsTeam().put(TeamEnum.TEAM2, 0);
        }
        else
        {
            for (Player player : players)
                roundManager.getWonRoundsPlayer().put(player, 0);
        }

        gameArena = arena;
        if (ladder.isBuild()) gameArena.setAvailable(false);

        // Load chunk
        for (Chunk chunk : gameArena.getCuboid().getChunks())
            chunk.load();

        // Hide players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            for (Player matchPlayer : players)
                if (!players.contains(onlinePlayer))
                    matchPlayer.hidePlayer(onlinePlayer);
        }

        switch (type)
        {
            case DUEL: Duel.startMatch(this); break;
            case PARTY_FFA: PartyFFA.startMatch(this); break;
            case PARTY_SPLIT: PartySplit.startMatch(this); break;
        }

        for (Player player : players)
        {
            matchStats.put(player, new PlayerMatchStat(player));
            SystemManager.getProfileManager().getProfiles().get(player).setStatus(ProfileStatus.MATCH);
        }

        roundManager.startRound();
    }

    public void endMatch()
    {
        // Remove match players
        for (Player player : players)
        {
            matchStats.get(player).end();
            removePlayer(player);
        }

        resetMap();

        // Remove spectators
        for (int i = 0; i < spectators.size(); i++)
            removePlayer(spectators.get(i));

        for (Chunk chunk : gameArena.getCuboid().getChunks())
            chunk.unload();

        status = MatchStatus.OLD;

        Bukkit.getPluginManager().callEvent(new MatchEndEvent(this));
    }


    public void addBlockChange(CachedBlock change)
    {
        for (CachedBlock c : blockChange)
        {
            if (c.getLocation().getX() == change.getLocation().getX() && c.getLocation().getY() == change.getLocation().getY() && c.getLocation().getZ() == change.getLocation().getZ())
                return;
        }
        blockChange.add(change);
    }

    public void resetMap()
    {
        for (Item is : droppedItems)
            is.remove();

        if (ladder.isBuild())
        {
            Rollback.rollBackArena(this);

            for (Chunk chunk : gameArena.getCuboid().getChunks())
            {
                for (Entity entity : chunk.getEntities())
                {
                    if (entity instanceof Item) entity.remove();
                }
            }
        }
    }


    public void sendMessage(String message, boolean spectator)
    {
        for (Player player : players)
        {
            Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

            if (profile.getStatus().equals(ProfileStatus.MATCH) && SystemManager.getMatchManager().getLiveMatchByPlayer(player).equals(this))
                player.sendMessage(StringUtil.CC(message));
        }
        if (spectator)
        {
            for (Player specPlayer : spectators)
                specPlayer.sendMessage(StringUtil.CC(message));
        }
    }

    public void addSpectator(Player player)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        spectators.add(player);

        // Hide spectator from match players
        for (Player matchPlayer : players)
            matchPlayer.hidePlayer(player);

        player.teleport(gameArena.getPosition3());
        SystemManager.getInventoryManager().getSpectatorInventory().setSpectatorInventory(player);
    }

    public void removePlayer(Player player)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        if ((profile.getStatus().equals(ProfileStatus.MATCH) && SystemManager.getMatchManager().getLiveMatchByPlayer(player).equals(this))
                || (profile.getStatus().equals(ProfileStatus.SPECTATE) && SystemManager.getMatchManager().getLiveMatchBySpectator(player).equals(this)))
        {
            spectators.remove(player);

            SystemManager.getInventoryManager().getSpawnInventory().setInventory(player, true);
        }
    }

}
