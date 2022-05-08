package dev.nandi0813.practice.Util;

import dev.nandi0813.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

public class AdMessageUtil
{

    public static void sendAdMessagesToConsole()
    {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Practice.getInstance(), () ->
        {
            ArrayList<String> messages = getAdMessages();
            if (!messages.isEmpty())
            {
                for (String message : messages)
                {
                    if (!message.startsWith("###"))
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("<br>", "\n")));
                }
            }
        }, 5L);
    }

    public static void sendAdMessagesToPlayer(Player player)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () ->
        {
            ArrayList<String> messages = getAdMessages();
            if (!messages.isEmpty())
            {
                for (String message : messages)
                {
                    if (!message.startsWith("###"))
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceFirst("<br>", "").replaceAll("<br>", "\n")));
                }
            }
        });
    }

    public static ArrayList<String> getAdMessages()
    {
        ArrayList<String> messages = new ArrayList<>();
        try {
            Connection mysql_connection = DriverManager.getConnection("jdbc:mysql://web.jztkft.hu:3306/c446ZonePracticeAD", "c446reader", "_M5HdmhsHG");
            String query = "SELECT message FROM messages";

            try (PreparedStatement stmt = mysql_connection.prepareStatement(query))
            {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next())
                    messages.add(resultSet.getString("message"));
            }
            catch (SQLException ignored) {}
        }
        catch (SQLException ignored) {}
    return messages;
    }

}
