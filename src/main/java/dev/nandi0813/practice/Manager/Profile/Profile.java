package dev.nandi0813.practice.Manager.Profile;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Profile
{

    @Getter private final UUID uuid;
    @Getter private final OfflinePlayer player;
    @Getter private final HashMap<Ladder, ItemStack[]> customKits = new HashMap<>();
    @Getter private final ProfileFile file;

    // Temporary Settings
    @Getter @Setter private ProfileStatus status;
    @Getter @Setter private boolean spectatorMode;
    @Getter @Setter private boolean party;
    @Getter @Setter private boolean hideSpectators;

    // Permanent Settings
    @Getter @Setter private boolean duelRequest;
    @Getter @Setter private boolean sidebar;
    @Getter @Setter private boolean hidePlayers;
    @Getter @Setter private boolean partyInvites;
    @Getter @Setter private boolean allowSpectate;
    @Getter @Setter private boolean messages;

    // Statistics
    @Getter @Setter private int unrankedWins;
    @Getter @Setter private int unrankedLosses;
    @Getter @Setter private int rankedWins;
    @Getter @Setter private int rankedLosses;
    @Getter private final HashMap<Ladder, Integer> ladderUnRankedWins = new HashMap<>();
    @Getter private final HashMap<Ladder, Integer> ladderUnRankedLosses = new HashMap<>();
    @Getter private final HashMap<Ladder, Integer> ladderRankedWins = new HashMap<>();
    @Getter private final HashMap<Ladder, Integer> ladderRankedLosses = new HashMap<>();
    @Getter private final HashMap<Ladder, Integer> elo = new HashMap<>();

    public Profile(UUID uuid)
    {
        this.uuid = uuid;
        player = Bukkit.getOfflinePlayer(uuid);
        status = ProfileStatus.OFFLINE;

        file = new ProfileFile(this);
    }

    public void saveData()
    {
        file.setProfileData();
    }
    public void getData() { file.getProfileData(); }

}
