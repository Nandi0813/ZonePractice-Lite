package dev.nandi0813.practice.Manager.Inventory.SpawnInventory;

import dev.nandi0813.practice.Manager.File.ConfigManager;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Server.ServerManager;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.PlayerUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class SpawnInventory
{

    @Getter private final File file = new File(Practice.getInstance().getDataFolder() + "/inventories", "spawnitems.yml");
    private final YamlConfiguration config;

    @Getter private static ItemStack unrankedItem;
    @Getter private static ItemStack rankedItem;
    @Getter private static ItemStack partyItem;
    @Getter private static ItemStack statsItem;
    @Getter private static ItemStack kiteditorItem;

    public SpawnInventory()
    {
        Bukkit.getPluginManager().registerEvents(new SpawnInventoryListener(), Practice.getInstance());
        if (!file.exists()) Practice.getInstance().saveResource("inventories/spawnitems.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
        getInventory();
    }

    public void getInventory()
    {
        unrankedItem = (ItemStack) config.get("unranked.item");
        rankedItem = (ItemStack) config.get("ranked.item");
        partyItem = (ItemStack) config.get("party.item");
        statsItem = (ItemStack) config.get("stats.item");
        kiteditorItem = (ItemStack) config.get("kiteditor.item");
    }

    public void setInventory(Player player, boolean teleport)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        PlayerUtil.setPlayerData(player, false, true);

        if (profile.isParty())
        {
            SystemManager.getInventoryManager().getPartyInventory().setPartyInventory(player);
        }
        else
        {
            player.getInventory().setItem(config.getInt("unranked.slot"), unrankedItem);
            if ((profile.getUnrankedWins() >= ConfigManager.getInt("ranked.min-unranked-wins")) || player.hasPermission("zonepractice.bypass.ranked.requirements"))
                player.getInventory().setItem(config.getInt("ranked.slot"), rankedItem);
            player.getInventory().setItem(config.getInt("party.slot"), partyItem);
            player.getInventory().setItem(config.getInt("stats.slot"), statsItem);
            player.getInventory().setItem(config.getInt("kiteditor.slot"), kiteditorItem);
        }

        player.updateInventory();
        if (ServerManager.getLobby() != null && teleport) player.teleport(ServerManager.getLobby());

        profile.setStatus(ProfileStatus.LOBBY);
    }

}
