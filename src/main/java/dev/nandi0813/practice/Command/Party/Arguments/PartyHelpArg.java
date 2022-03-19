package dev.nandi0813.practice.Command.Party.Arguments;

import dev.nandi0813.practice.Manager.File.LanguageManager;
import org.bukkit.entity.Player;

public class PartyHelpArg
{

    public static void HelpCommand(Player player, String label)
    {
        for (String line : LanguageManager.getList("party.help"))
            player.sendMessage(line.replaceAll("%label%", label));
    }

}
