package dev.nandi0813.practice.Manager.Ladder.Custom.Gui;

import dev.nandi0813.practice.Manager.Ladder.Custom.CustomLadderManager;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;

public class KitEditorGui
{

    @Getter private static final ItemStack fillerItem = ItemUtil.createItem(" ", Material.STAINED_GLASS_PANE, Short.valueOf("1"));
    @Getter private static final ItemStack dummyItem = ItemUtil.createItem("dummy", Material.GLOWSTONE_DUST);

    public static void openGui(Player player, Ladder ladder)
    {
        Profile profile = SystemManager.getProfileManager().getProfiles().get(player);

        player.openInventory(buildGui(ladder));
        buildPlayerInventory(profile, ladder);

        profile.setStatus(ProfileStatus.EDITOR);
        CustomLadderManager.getOpenedEditor().put(player, ladder);
        CustomLadderManager.getOpenedKit().put(player, ladder);
    }

    public static Inventory buildGui(Ladder ladder)
    {
        Inventory gui = InventoryUtil.createInventory("&8Editing " + ladder.getName() + " Kit", 6);

        gui.setItem(0, ItemUtil.createItem("&6&lEditing: &eKit " + ladder.getName(), Material.NAME_TAG));
        gui.setItem(6, ItemUtil.createItem("&a&lSave", Material.WOOL, Short.valueOf("5"), getSaveLore()));
        gui.setItem(7, ItemUtil.createItem("&e&lLoad Default Kit", Material.WOOL, Short.valueOf("4"), getDefaultKitLore()));
        gui.setItem(8, ItemUtil.createItem("&c&lCancel", Material.WOOL, Short.valueOf("14"), getCancelLore()));

        // Frame
        for (int i : new int[]{1,2,3,5,9,10,11,12,13,14,15,16,17,19,28,37,46})
            gui.setItem(i, CustomLadderManager.getFillerItem());

        gui.setItem(4, getEffectItem(ladder));

        ArrayList<ItemStack> armorContent = new ArrayList<>(Arrays.asList(ladder.getArmor()));
        for (int i : new int[]{18,27,36,45})
        {
            if (armorContent.get(Math.abs(i / 9 - 5)) != null)
                gui.setItem(i, armorContent.get(Math.abs(i / 9 - 5)));
            else
                gui.setItem(i, dummyItem);
        }

        for (int i = 0; i < gui.getSize(); i++)
            if (gui.getItem(i) == null) gui.setItem(i, fillerItem);

        gui.remove(dummyItem);
        return gui;
    }

    public static void buildPlayerInventory(Profile profile, Ladder ladder)
    {
        Player player = profile.getPlayer().getPlayer();
        player.getInventory().clear();

        ItemStack[] content = profile.getCustomKits().get(ladder);

        if (content != null)
            player.getInventory().setContents(content);
        else
            player.getInventory().setContents(ladder.getInventory());
    }

    private static ItemStack getEffectItem(Ladder ladder)
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&eYou will get these effects,");
        lore.add("&ewhen the game starts.");
        lore.add("");
        if (ladder.getEffects().size() > 0)
        {
            for (PotionEffect potionEffect : ladder.getEffects())
                lore.add("&d" + potionEffect.getType().getName() + " " + (potionEffect.getAmplifier()+1) + " &7for " + StringUtil.formatMillisecondsToMinutes((potionEffect.getDuration()/20)* 1000L));
        }
        else lore.add("&7This ladder has no effects.");
        lore.add("");

        return ItemUtil.createItem("&5Effects", Material.POTION, Short.valueOf("8233"), lore);
    }

    public static ArrayList<String> getSaveLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&e&lClick here &eto save");
        lore.add("&ethe custom kit.");
        lore.add("");
        return lore;
    }

    public static ArrayList<String> getDefaultKitLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&e&lClick here &eto load the default");
        lore.add("&ekit to your inventory.");
        lore.add("");
        return lore;
    }

    public static ArrayList<String> getCancelLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&e&lClick here &eto abort editing the");
        lore.add("&ekit, and return to the kit menu.");
        lore.add("");
        return lore;
    }

}
