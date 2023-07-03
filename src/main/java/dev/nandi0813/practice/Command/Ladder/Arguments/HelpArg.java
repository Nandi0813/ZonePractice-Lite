package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class HelpArg
{

    public static void run(Player player, String label)
    {
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " list &7- &fList all the ladders."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " info <id>/<name> &7- &fGet information on a ladder."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setname <id> <name> &7- &fSet the name."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " seticon <id>/<name> &7- &fSet the icon."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setinv <id>/<name> &7- &fSet the inventory, armor and potion effects."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setcombo <id>/<name> &7- &fEnable/Disable combo knockback."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " hitdelay <id>/<name> <hit_delay> &7- &fSet the hit delay."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setranked <id>/<name> &7- &fEnable/Disable ranked games."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " seteditable <id>/<name> &7- &fEnable/Disable to make the ladder editable."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setregen <id>/<name> &7- &fEnable/Disable regeneration."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " sethunger <id>/<name> &7- &fEnable/Disable hunger."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setbuild <id>/<name> &7- &fEnable/Disable building."));
        player.sendMessage(StringUtil.CC(" &c» /" + label + " setenable <id>/<name> &7- &fEnable/Disable the ladder."));
        player.sendMessage(StringUtil.CC("&c&m------------------------------------------------"));
    }

}
