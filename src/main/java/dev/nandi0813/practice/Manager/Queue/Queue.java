package dev.nandi0813.practice.Manager.Queue;

import dev.nandi0813.practice.Event.QueueEndEvent;
import dev.nandi0813.practice.Event.QueueStartEvent;
import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.RankedGui;
import dev.nandi0813.practice.Manager.Gui.UnrankedGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Match.Match;
import dev.nandi0813.practice.Manager.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public class Queue
{

    private final QueueManager queueManager = SystemManager.getQueueManager();

    @Getter @Setter private Player player;
    @Getter private final Profile profile;
    @Getter @Setter private Ladder ladder;
    @Getter @Setter private boolean ranked;
    @Getter @Setter private int range;
    @Getter private final QueueRunnable queueRunnable = new QueueRunnable(this);
    @Getter private BukkitTask eloRunnable;
    private final int rangeIncrease = ConfigManager.getConfig().getInt("ranked.elo-range-increase");

    public Queue(Player player, Ladder ladder, boolean ranked)
    {
        this.player = player;
        profile = SystemManager.getProfileManager().getProfiles().get(player);
        this.ladder = ladder;
        this.ranked = ranked;
        this.range = ConfigManager.getConfig().getInt("ranked.elo-range-increase");
    }

    /**
     * It starts the queue
     */
    public void startQueue()
    {
        QueueStartEvent queueStartEvent = new QueueStartEvent(this);
        Bukkit.getPluginManager().callEvent(queueStartEvent);

        if (!queueStartEvent.isCancelled())
        {
            profile.setStatus(ProfileStatus.QUEUE);
            SystemManager.getInventoryManager().getQueueInventory().setQueueInventory(player);
            queueManager.getQueues().add(this);

            queueRunnable.begin();
            player.sendMessage(LanguageManager.getString("queue.queue-start").replaceAll("%weightClass%", (ranked ? "ranked" : "unranked")).replaceAll("%ladderName%", ladder.getName()));

            if (ranked)
            {
                eloRunnable = new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        int elo = profile.getElo().get(ladder);
                        if (queueManager.getQueue(getPlayer()) == null || elo - getRange() <= 0)
                        {
                            queueRunnable.cancel();
                            this.cancel();
                            return;
                        }
                        for (Queue queue : queueManager.getQueues())
                        {
                            if (queue != queueManager.getQueue(player)
                                    && queue.getPlayer() != player
                                    && queue.getLadder() == ladder
                                    && queue.isRanked() == ranked)
                            {
                                int queueelo = queue.getProfile().getElo().get(ladder);
                                if ((elo - range) <= queueelo && (elo + range) >= queueelo)
                                {
                                    Bukkit.getScheduler().runTask(Practice.getInstance(), () -> startMatch(queue));

                                    queueRunnable.cancel();
                                    this.cancel();
                                    return;
                                }
                            }
                        }

                        player.sendMessage(LanguageManager.getString("queue.elo-range")
                                .replaceAll("%eloMin%", String.valueOf((elo - range)))
                                .replaceAll("%eloMax%", String.valueOf((elo + range)))
                                .replaceAll("%playerElo%", String.valueOf(elo)));

                        if (range < ConfigManager.getInt("ranked.max-range"))
                            setRange(range + rangeIncrease);

                    }
                }.runTaskTimerAsynchronously(Practice.getInstance(), 0, 20L * ConfigManager.getConfig().getInt("ranked.elo-range-time"));
            }
            else
            {
                for (Queue queue : queueManager.getQueues())
                {
                    if (queue != queueManager.getQueue(player)
                            && queue.getPlayer() != player
                            && queue.getLadder() == ladder
                            && queue.isRanked() == ranked)
                    {
                        startMatch(queue);
                        return;
                    }
                }
            }

            UnrankedGui.updateGui();
            RankedGui.updateGui();
        }
    }

    /**
     * If there is an arena available, start a match with the player in the queue and the player who called the function
     *
     * @param queue The queue of the player you want to start a match with.
     */
    public void startMatch(Queue queue)
    {
        Arena arena = SystemManager.getArenaManager().getRandomArena(ladder.isBuild());
        if (arena != null)
        {
            queue.endQueue(true);
            endQueue(true);

            Match match = new Match(MatchType.DUEL, Arrays.asList(player, queue.getPlayer()), ladder, ranked, arena);
            match.startMatch();

            UnrankedGui.updateGui();
            RankedGui.updateGui();
            return;
        }

        queue.getPlayer().sendMessage(LanguageManager.getString("queue.no-arena"));
        queue.endQueue(false);

        player.sendMessage(LanguageManager.getString("queue.no-arena"));
        endQueue(false);

    }

    /**
     * This function is called when the queue ends, and it cancels the queue runnable, and if the player is online, it
     * sends them a message saying that the queue has ended
     *
     * @param foundMatch Whether or not the player was found a match.
     */
    public void endQueue(boolean foundMatch)
    {
        QueueEndEvent queueEndEvent = new QueueEndEvent(this);
        Bukkit.getPluginManager().callEvent(queueEndEvent);

        queueManager.getQueues().remove(this);

        queueRunnable.cancel();
        if (eloRunnable != null) eloRunnable.cancel();

        if (!foundMatch && player.isOnline())
        {
            SystemManager.getInventoryManager().getSpawnInventory().setInventory(player, false);
            player.sendMessage(LanguageManager.getString("queue.queue-end").replaceAll("%weightClass%", (ranked ? "ranked" : "unranked")).replaceAll("%ladderName%", ladder.getName()));
        }

        UnrankedGui.updateGui();
        RankedGui.updateGui();
    }

}
