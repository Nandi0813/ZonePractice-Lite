package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class InfoArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " info <ladder_id>/<ladder_name>"));
            return;
        }

        Ladder ladder;
        if (StringUtil.isInteger(args[1]))
            ladder = Practice.getLadderManager().getLadder(Integer.parseInt(args[1]));
        else
            ladder = Practice.getLadderManager().getLadder(args[1]);

        if (ladder == null)
        {
            player.sendMessage(StringUtil.CC("&cInvalid ladder id or name."));
            return;
        }

        player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
        player.sendMessage(StringUtil.CC(" &7» &6Ladder ID: &f" + ladder.getId()));
        player.sendMessage(StringUtil.CC(" &7» &6Name: &f" + (ladder.getName() != null ? ladder.getName() : "&cnull")));
        player.sendMessage(StringUtil.CC(" &7» &6Status: " + StringUtil.getStatus(ladder.isEnabled()) + "d"));
        player.sendMessage("");
        player.sendMessage(StringUtil.CC(" &7» &6Icon Material: &f" + (ladder.getIcon() != null ? ladder.getIcon().getType() : "&cnull")));
        player.sendMessage("");
        player.sendMessage(StringUtil.CC(" &7» &6Editable: " + StringUtil.getStatus(ladder.isEditable()) + "d"));
        player.sendMessage(StringUtil.CC(" &7» &6Regeneration: " + StringUtil.getStatus(ladder.isRegen()) + "d"));
        player.sendMessage(StringUtil.CC(" &7» &6Hunger: " + StringUtil.getStatus(ladder.isHunger()) + "d"));
        player.sendMessage(StringUtil.CC(" &7» &6Building: " + StringUtil.getStatus(ladder.isBuild()) + "d"));
        player.sendMessage(StringUtil.CC(" &7» &6Ranked: " + StringUtil.getStatus(ladder.isEditable()) + "d"));
        player.sendMessage("");
        player.sendMessage(StringUtil.CC(" &7» &6Knockback: &f" + ladder.getKnockbackType().getName()));
        player.sendMessage(StringUtil.CC(" &7» &6Hit Delay: &f" + ladder.getHitDelay()));
        player.sendMessage("");
        player.sendMessage(StringUtil.CC(" &7» &6Effects: &f" + (ladder.getEffects().isEmpty() ? "&cnull" : InventoryUtil.getPotionEffectNames(ladder.getEffects()).toString().replace("[", "").replace("]", ""))));
        player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
    }

}
