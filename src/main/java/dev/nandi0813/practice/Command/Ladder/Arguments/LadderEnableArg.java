package dev.nandi0813.practice.Command.Ladder.Arguments;

import dev.nandi0813.practice.Manager.File.BackendManager;
import dev.nandi0813.practice.Manager.Gui.RankedGui;
import dev.nandi0813.practice.Manager.Gui.UnrankedGui;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Manager.SystemManager;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

public class LadderEnableArg
{

    public static void LadderEnableCommand(Player player, String label, String[] args)
    {
        if (args.length == 2)
        {
            Ladder ladder = SystemManager.getLadderManager().getLadder(args[1]);

            if (ladder != null)
            {
                if (!ladder.isEnabled())
                {
                    ladder.setEnabled(true);
                    SystemManager.getLadderManager().getDisabledLadders().remove(ladder.getName());
                    BackendManager.getConfig().set("disabled-ladders", SystemManager.getLadderManager().getDisabledLadders());
                    BackendManager.save();

                    UnrankedGui.updateGui();
                    RankedGui.updateGui();

                    player.sendMessage(StringUtil.CC("&eYou successfully &aenabled &ethe " + ladder.getName() + " &aladder."));
                }
                else
                    player.sendMessage(StringUtil.CC("&cLadder is already enabled."));
            }
            else
                player.sendMessage(StringUtil.CC("&cLadder cannot be found."));
        }
        else
            player.sendMessage(StringUtil.CC("&c/" + label + " enable <ladder>"));
    }

}
