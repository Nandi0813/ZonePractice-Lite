package dev.nandi0813.practice.Command.Party;

import dev.nandi0813.practice.Command.Party.Arguments.*;
import dev.nandi0813.practice.Manager.SystemManager;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor
{

    @Getter private static final String prefix = "&8[&3&lParty&8] ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (args.length == 0)
                PartyHelpArg.HelpCommand(player, label);

            else if (args[0].equalsIgnoreCase("create"))
                SystemManager.getPartyManager().createParty(player);

            else if (args[0].equalsIgnoreCase("invite"))
                PartyInviteArg.InviteCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("accept"))
                PartyAcceptArg.AcceptCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("leave"))
                PartyLeaveArg.LeaveCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("kick"))
                PartyKickArg.KickCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("leader"))
                PartyLeaderArg.LeaderCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("disband"))
                PartyDisbandArg.DisbandCommand(player, label, args);

            else if (args[0].equalsIgnoreCase("info"))
                PartyInfoArg.InfoCommand(player, label, args);

            else
                PartyHelpArg.HelpCommand(player, label);

        }
        return true;
    }

}
