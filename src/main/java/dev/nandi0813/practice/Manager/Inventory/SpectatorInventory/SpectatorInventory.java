package dev.nandi0813.practice.Manager.Inventory.SpectatorInventory;

import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.PlayerUtil;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class SpectatorInventory
{

    @Getter File file = new File(Practice.getInstance().getDataFolder() + "/inventories", "spectatoritems.yml");
    private final YamlConfiguration config;

    @Getter private static ItemStack leaveItemSpectate;


    public SpectatorInventory()
    {
        if (!file.exists()) Practice.getInstance().saveResource("inventories/spectatoritems.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
        getInventory();
    }

    public void getInventory()
    {
        leaveItemSpectate = (ItemStack) config.get("spectate.leave.item");
        ItemUtil.hideItemFlags(leaveItemSpectate);
    }

    public void setSpectatorInventory(Player player)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);
        profile.setStatus(ProfileStatus.SPECTATE);

        PlayerUtil.setPlayerData(player, true, false);
        player.getInventory().setItem(config.getInt("spectate.leave.slot"), leaveItemSpectate);

        player.updateInventory();
    }

}
