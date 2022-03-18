package dev.nandi0813.practice.Manager.Match.MatchType.Duel;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ClickableText
{

    public static TextComponent getMessager(String matchID, Player winner, Player loser)
    {
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aWinner: "));

        TextComponent winnerText = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&e" + winner.getName()));
        winnerText.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, ChatColor.translateAlternateColorCodes('&', "/inv " + matchID + " " + winner.getName())));
        winnerText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aClick to view inventory of &6" + winner.getName() + "&a.")).create()));

        TextComponent loserText = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&e" + loser.getName()));
        loserText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ChatColor.translateAlternateColorCodes('&', "/inv " + matchID + " " + loser.getName())));
        loserText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aClick to view inventory of &6" + loser.getName() + "&a.")).create()));

        message.addExtra(winnerText);
        message.addExtra(ChatColor.translateAlternateColorCodes('&', "&7 - &cLoser: &e"));
        message.addExtra(loserText);
        return message;
    }

}
