package dev.nandi0813.practice.Util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtil
{

    public static String CC(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> CC(List<String> stringlist)
    {
        List<String> list = new ArrayList<String>();
        for (String string : stringlist)
        {
            list.add(CC(string));
        }
        return list;
    }

    public static String getStatus(boolean status)
    {
        if (status) return StringUtil.CC("&aEnable");
        return StringUtil.CC("&cDisable");
    }

    public static String formatMillisecondsToHours(long l)
    {
        int h1 = (int)(l / 1000L) % 60;
        int h2 = (int)(l / 60000L % 60L);
        int h3 = (int)(l / 3600000L % 24L);
        return String.format("%02d:%02d:%02d", h3, h2, h1);
    }

    public static String formatMillisecondsToMinutes(long l)
    {
        int h1 = (int)(l / 1000L) % 60;
        int h2 = (int)(l / 60000L % 60L);
        return String.format("%02d:%02d", h2, h1);
    }

    public static String formatMillisecondsToSeconds(long l)
    {
        float f = (l + 0.0f) / 1000.0f;
        String string = String.format("%1$.1f", f);
        return string.replace(",", ".");
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
