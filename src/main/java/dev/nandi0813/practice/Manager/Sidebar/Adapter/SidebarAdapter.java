package dev.nandi0813.practice.Manager.Sidebar.Adapter;

import org.bukkit.entity.Player;

import java.util.List;

public interface SidebarAdapter
{

    String getTitle(Player player);

    List<String> getLines(Player player);

}
