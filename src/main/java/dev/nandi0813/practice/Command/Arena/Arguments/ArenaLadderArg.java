package dev.nandi0813.practice.Command.Arena.Arguments;

import dev.nandi0813.practice.Manager.Arena.Arena;
import dev.nandi0813.practice.Manager.Arena.Util.ArenaUtil;
import dev.nandi0813.practice.Manager.Ladder.Ladder;
import dev.nandi0813.practice.Practice;
import dev.nandi0813.practice.Util.StringUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaLadderArg
{

    public static void LadderCommand(Player player, String label, String[] args)
    {

        if (args.length == 3 || args.length == 4)
        {
            if (args[1].equalsIgnoreCase("list"))
            {
                if (args.length == 3)
                {
                    String arenaName = args[2];
                    Arena arena = Practice.getArenaManager().getArena(arenaName);

                    if (arena != null)
                    {
                        List<String> ladderNames = ArenaUtil.getLadderNames(arena);

                        player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
                        player.sendMessage(StringUtil.CC(" &7» &3" + arena.getName() + " &7arena ladders:"));
                        player.sendMessage("");

                        if (ladderNames.isEmpty())
                            player.sendMessage(StringUtil.CC(" &cNULL"));
                        else
                            player.sendMessage(StringUtil.CC(" &b" + ladderNames.toString().replace("[", "").replace("]", "")));

                        player.sendMessage(StringUtil.CC("&7&m-----------------------------------"));
                    }
                    else
                    {
                        player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
                    }
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder list <name>"));
                }
            }
            else if (args[1].equalsIgnoreCase("add"))
            {
                if (args.length == 4)
                {
                    String arenaName = args[2];
                    Arena arena = Practice.getArenaManager().getArena(arenaName);

                    if (arena != null)
                    {
                        if (arena.isEnabled())
                        {
                            player.sendMessage(StringUtil.CC("&cYou can't edit a enabled arena."));
                            return;
                        }

                        String ladderName = args[3];
                        Ladder ladder = Practice.getLadderManager().getLadder(ladderName);

                        if (ladder != null)
                        {
                            if ((arena.isBuild() && ladder.isBuild()) || (!arena.isBuild() && !ladder.isBuild()))
                            {
                                if (!arena.getLadders().contains(ladder))
                                {
                                    arena.getLadders().add(ladder);
                                    arena.saveData();
                                    player.sendMessage(StringUtil.CC("&aYou added &e" + ladder.getName() + " ladder &afor arena &6" + arena.getName() + "&a."));
                                }
                                else
                                {
                                    player.sendMessage(StringUtil.CC("&c" + ladder.getName() + " ladder is already added for arena " + arena.getName()));
                                }
                            }
                            else
                            {
                                player.sendMessage(StringUtil.CC("&cYou can't add build ladders to non-build arenas and vice versa."));
                            }
                        }
                    }
                    else
                    {
                        player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
                    }
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder add <name> <ladder>"));
                }
            }
            else if (args[1].equalsIgnoreCase("remove"))
            {
                if (args.length == 4)
                {
                    String arenaName = args[2];
                    Arena arena = Practice.getArenaManager().getArena(arenaName);

                    if (arena != null)
                    {
                        if (arena.isEnabled())
                        {
                            player.sendMessage(StringUtil.CC("&cYou can't edit a enabled arena."));
                            return;
                        }

                        String ladderName = args[3];
                        Ladder ladder = Practice.getLadderManager().getLadder(ladderName);

                        if (ladder != null)
                        {
                            if (arena.getLadders().contains(ladder))
                            {
                                arena.getLadders().remove(ladder);
                                arena.saveData();
                                player.sendMessage(StringUtil.CC("&aYou removed &e" + ladder.getName() + " ladder &afrom arena &6" + arena.getName() + "&a."));
                            }
                            else
                            {
                                player.sendMessage(StringUtil.CC("&c" + ladder.getName() + " ladder isn't added for arena " + arena.getName()));
                            }
                        }
                        else
                        {
                            player.sendMessage(StringUtil.CC("&c" + ladderName + " ladder doesn't exists."));
                        }
                    }
                    else
                    {
                        player.sendMessage(StringUtil.CC("&c" + arenaName + " arena doesn't exists."));
                    }
                }
                else
                {
                    player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder remove <name> <ladder>"));
                }
            }
            else
            {
                player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder list <name>"));
                player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder <add/remove> <name> <ladder>"));
            }
        }
        else
        {
            player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder list <name>"));
            player.sendMessage(StringUtil.CC(" &c » /" + label + " ladder <add/remove> <name> <ladder>"));
        }
    }

}
