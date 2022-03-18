package dev.nandi0813.practice.Manager.Ladder.Custom;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomLadderManager
{
    @Getter private static final HashMap<Player, Ladder> openedEditor = new HashMap<>();
    @Getter private static final HashMap<Player, Ladder> openedKit = new HashMap<>();
    @Getter private static final List<Player> backToSum = new ArrayList<>();

    @Getter private static final ItemStack fillerItem = ItemUtil.createItem(" ", Material.STAINED_GLASS_PANE, Short.valueOf("15"));

    public CustomLadderManager(Practice practice)
    {
        Bukkit.getPluginManager().registerEvents(new CustomLadderListener(), practice);
    }

}
