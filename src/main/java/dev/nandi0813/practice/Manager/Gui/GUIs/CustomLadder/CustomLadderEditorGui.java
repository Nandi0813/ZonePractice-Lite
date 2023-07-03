package dev.nandi0813.practice.Manager.Gui.GUIs.CustomLadder;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import dev.nandi0813.practice.Manager.Gui.GUI;
import dev.nandi0813.practice.Manager.Gui.GUIManager;
import dev.nandi0813.practice.Manager.Gui.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileStatus;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.ItemUtil;
import dev.nandi0813.practice.Util.StringUtil;
import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomLadderEditorGui extends GUI
{

    @Getter private static final ItemStack fillerItem = ItemUtil.createItem(" ", Material.STAINED_GLASS_PANE, Short.valueOf("1"));
    @Getter private static final ItemStack dummyItem = ItemUtil.createItem("dummy", Material.GLOWSTONE_DUST);

    @Getter private final Profile profile;
    @Getter private final Ladder ladder;
    private final GUI backTo;

    public CustomLadderEditorGui(Profile profile, Ladder ladder, GUI backTo)
    {
        super(GUIType.CUSTOM_LADDER_EDITOR);
        this.profile = profile;
        this.ladder = ladder;
        this.backTo = backTo;

        this.gui.put(1, InventoryUtil.createInventory(LanguageManager.getString("gui.kit-editor.editor.title").replaceAll("%ladderName%", ladder.getName()), 1));

        build();
    }

    @Override
    public void build()
    {
        update();
    }

    @Override
    public void update()
    {
        Inventory inventory = gui.get(1);
        inventory.clear();

        inventory.setItem(7, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.load-item.name"), Material.WOOL, Short.valueOf("4"), LanguageManager.getList("gui.kit-editor.editor.load-item.lore")));
        inventory.setItem(8, ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.save-item.name"), Material.WOOL, Short.valueOf("5"), LanguageManager.getList("gui.kit-editor.editor.save-item.lore")));

        // Frame
        for (int i : new int[]{4,6})
            inventory.setItem(i, GUIManager.getFillerItem());

        inventory.setItem(5, getEffectItem(ladder));

        List<ItemStack> armorContent = new ArrayList<>(Arrays.asList(ladder.getArmor()));
        Collections.reverse(armorContent);
        for (int i : new int[]{0,1,2,3})
        {
            ItemStack armor = armorContent.get(i);
            if (armor != null)
            {
                ItemMeta armorMeta = armor.getItemMeta();
                armorMeta.setDisplayName(LanguageManager.getString("gui.kit-editor.editor.auto-equip-item-name"));
                armor.setItemMeta(armorMeta);

                inventory.setItem(i, armor);
            }
            else
                inventory.setItem(i, dummyItem);
        }

        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null) inventory.setItem(i, fillerItem);

        inventory.remove(dummyItem);

        updatePlayers();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        int slot = e.getRawSlot();
        InventoryAction action = e.getAction();

        if (inventory.getSize() > slot && !action.equals(InventoryAction.DROP_ONE_CURSOR) && !action.equals(InventoryAction.DROP_ALL_CURSOR))
            e.setCancelled(true);

        if (slot == 8)
        {
            backTo.open(player);
            backTo.update();
        }
        else if (slot == 7)
            player.getInventory().setContents(ladder.getInventory());
    }

    @Override
    public void open(Player player)
    {
        open(player, 1);

        Profile playerProfile = Practice.getProfileManager().getProfiles().get(player);
        playerProfile.setStatus(ProfileStatus.EDITOR);

        player.getInventory().clear();

        if (profile.getCustomKits().containsKey(ladder))
            player.getInventory().setContents(profile.getCustomKits().get(ladder));
        else
            player.getInventory().setContents(ladder.getInventory());
    }



    private static ItemStack getEffectItem(Ladder ladder)
    {
        List<String> lore = new ArrayList<>();
        List<String> effectList = new ArrayList<>();

        if (ladder.getEffects().size() > 0)
        {
            for (PotionEffect potionEffect : ladder.getEffects())
                effectList.add("&d" + getPotionEffectNormalName(potionEffect.getType().getName()) + " " + (potionEffect.getAmplifier()+1) + " &7for " + StringUtil.formatMillisecondsToMinutes((potionEffect.getDuration()/20)* 1000L));
        }
        else
            effectList.add("&7This ladder has no effects.");

        for (String line : LanguageManager.getList("gui.kit-editor.editor.effect-item.lore"))
        {
            if (line.contains("%effects%"))
                lore.addAll(effectList);
            else
                lore.add(line);
        }

        return ItemUtil.createItem(LanguageManager.getString("gui.kit-editor.editor.effect-item.name"), Material.POTION, Short.valueOf("8233"), lore);
    }

    public static String getPotionEffectNormalName(String base)
    {
        return WordUtils.capitalize(base.replaceAll("_", "").toLowerCase());
    }

}
