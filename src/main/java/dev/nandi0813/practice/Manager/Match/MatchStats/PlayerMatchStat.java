package dev.nandi0813.practice.Manager.Match.MatchStats;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerMatchStat
{

    @Getter private final OfflinePlayer player;
    @Getter @Setter private boolean isSet;

    @Getter private final HashMap<Long, Integer> cps = new HashMap<>();
    @Getter private double averageCPS;
    @Getter @Setter private int hit = 0;
    @Getter @Setter private int getHit = 0;
    @Getter @Setter private int longestCombo = 0;

    @Getter @Setter private double endHeart = 0;
    @Getter @Setter private double endHunger = 0;
    @Getter @Setter private List<PotionEffect> endPotionEffects = new ArrayList<>();
    @Getter @Setter private ItemStack[] endArmor = null;
    @Getter @Setter private ItemStack[] endInventory = null;

    public PlayerMatchStat(OfflinePlayer player)
    {
        this.player = player;
    }

    public void end()
    {
        isSet = true;

        int sumCps = cps.values().stream().mapToInt(Integer::intValue).sum();
        averageCPS = sumCps * 1.0 / cps.size();
        if (averageCPS < 2) averageCPS = 0;

        Player online = player.getPlayer();

        if (online != null)
        {
            if (MatchStatListener.getCurrentCombo().containsKey(online))
            {
                int combo = MatchStatListener.getCurrentCombo().get(online);
                if (combo > longestCombo)
                    longestCombo = combo;
            }

            endHeart = online.getHealth(); // Nem jó az érték, mivel már heal után van a játékos..
            endHunger = online.getFoodLevel();
            endPotionEffects.addAll(online.getActivePotionEffects());
            endArmor = online.getInventory().getArmorContents();
            endInventory = online.getInventory().getContents();
        }

        MatchStatListener.getCurrentCombo().remove(online);
    }

}
