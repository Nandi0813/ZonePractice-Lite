package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class LadderInfoArg
{

    public static void InfoCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            String ladderName = args[1];
            Ladder ladder = SystemManager.getLadderManager().getLadder(ladderName);

            if (ladder != null)
            {
                player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
                player.sendMessage(StringUtil.CC(" &7» &3Ladder: &b" + ladder.getName()));
                if (ladder.getIcon() != null)
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Icon Material: &b" + ladder.getIcon().getType()));
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &7» &3Icon Material: &cnull"));
                }
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Editable: " + StringUtil.getStatus(ladder.isEditable())));
                player.sendMessage(StringUtil.CC(" &7» &3Regeneration: " + StringUtil.getStatus(ladder.isRegen())));
                player.sendMessage(StringUtil.CC(" &7» &3Hunger: " + StringUtil.getStatus(ladder.isHunger())));
                player.sendMessage(StringUtil.CC(" &7» &3Building: " + StringUtil.getStatus(ladder.isBuild())));
                player.sendMessage(StringUtil.CC(" &7» &3Ranked: " + StringUtil.getStatus(ladder.isEditable())));
                player.sendMessage("");
                player.sendMessage(StringUtil.CC(" &7» &3Hit Delay: &b" + ladder.getHitDelay()));
                // player.sendMessage(StringUtil.CC(" &7» &3Effects: &b" + ladder.getEffectNames()));
                player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
            }
            else
            {
                player.sendMessage(StringUtil.CC("&c" + ladderName + " ladder doesn't exists."));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC("&c/" + label + " info <name>"));
        }
    }

}
