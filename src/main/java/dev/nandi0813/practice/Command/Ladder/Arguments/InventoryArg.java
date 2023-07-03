package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class InventoryArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " setinv <ladder_id>/<ladder_name>"));
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

        if (ladder.isEnabled())
        {
            player.sendMessage(StringUtil.CC("&cYou can't edit an enabled ladder."));
            return;
        }

        ladder.setArmor(player.getInventory().getArmorContents());
        ladder.setInventory(player.getInventory().getContents());
        ladder.setEffects((List<PotionEffect>) player.getActivePotionEffects());

        player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's inventory."));
    }

}
