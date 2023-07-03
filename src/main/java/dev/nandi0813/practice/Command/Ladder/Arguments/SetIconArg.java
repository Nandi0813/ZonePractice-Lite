package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetIconArg
{

    public static void run(Player player, String label, String[] args)
    {
        if (args.length != 2)
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " seticon <ladder_id>/<ladder_name>"));
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

        ItemStack icon = player.getItemInHand().clone();
        if (icon == null || icon.getType().equals(Material.AIR))
        {
            player.sendMessage(StringUtil.CC("&cYou have to hold the icon item in your hand."));
            return;
        }

        ladder.setIcon(icon);
        player.sendMessage(StringUtil.CC("&eYou &asuccessfully &eset the &6ID" + ladder.getId() + " &eladder's icon."));
    }

}
