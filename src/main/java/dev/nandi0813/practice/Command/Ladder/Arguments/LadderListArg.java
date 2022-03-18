package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class LadderListArg
{

    public static void ListCommand(Player player)
    {
        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
        for (Ladder ladder : SystemManager.getLadderManager().getLadders())
        {
            ClickableMessageUtil.sendClickableMessage(player, " &7» &e" + ladder.getName() + " &7- Build: " + StringUtil.getStatus(ladder.isBuild()), "/ladder info " + ladder.getName(), "&eClick here to view ladder info.");
        }
        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
    }

}
