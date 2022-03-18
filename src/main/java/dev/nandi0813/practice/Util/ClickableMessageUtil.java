package dev.nandi0813.practice.Util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ClickableMessageUtil
{

    public static void sendClickableMessage(Player player, String message, String command, String hovermessage)
    {
        TextComponent msg = new TextComponent(StringUtil.CC(message));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtil.CC(hovermessage)).create()));
        player.spigot().sendMessage(msg);
    }

}
