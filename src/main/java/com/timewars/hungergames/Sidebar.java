package com.timewars.hungergames;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class Sidebar {

    private HungerGames hungerGamesPlugin;
    private int taskID;

    public Sidebar(HungerGames hungerGamesPlugin) {
        this.hungerGamesPlugin = hungerGamesPlugin;
    }

    public void createBoard(Player player) {
        BPlayerBoard board = Netherboard.instance().createBoard(player,
                ChatColor.AQUA + "TimeWars");

        board.setAll(
                ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players"  ,
                Bukkit.getOnlinePlayers().size() + " alive",
                "   ",
                ChatColor.GOLD + ChatColor.BOLD.toString() + "Stats",
                "Kills: " + ChatColor.GREEN + player.getStatistic(Statistic.PLAYER_KILLS),
                "  " ,
                ChatColor.YELLOW + ChatColor.BOLD.toString() + "Time: " +
                        ChatColor.RESET + player.getStatistic(Statistic.TIME_SINCE_DEATH)
        );
    }

    public void startUpdatingSideBar(Player player) {

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(hungerGamesPlugin, new Runnable() {
            @Override
            public void run() {
                createBoard(player);
            }
        }, 0,20);
    }
}
