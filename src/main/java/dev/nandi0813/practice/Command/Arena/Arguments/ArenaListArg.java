package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.ClickableMessageUtil;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class ArenaListArg
{

    public static void ListCommand(Player player)
    {
        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
        for (Arena arena : SystemManager.getArenaManager().getArenas())
        {
            ClickableMessageUtil.sendClickableMessage(player, " &7» &e" + arena.getName() + " &7- Status: " + StringUtil.getStatus(arena.isEnabled()), "/arena info " + arena.getName(), "&eClick here to view arena info.");
        }
        player.sendMessage(StringUtil.CC("&7&m----------------------------"));
    }

}
