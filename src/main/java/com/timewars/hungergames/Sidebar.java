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
                "Kills: " + ChatColor.GREEN + player.getStatistic(Statistic.PLAYER_KILLS), //count in deathevent
                "  " ,
                ChatColor.YELLOW + ChatColor.BOLD.toString() + "Time: " +
                        ChatColor.RESET +
                        (getGameTime())
        );

    }

    public String getGameTime()
    {
        long seconds = (Bukkit.getWorld("world").getTime() - HungerGames.game.getTimeStarted()) / 20;

        String time = null;

        long minutes = seconds/60;

        if (minutes > 0 ) return (minutes + " min " + (seconds - minutes*60) + " s");

        return (seconds + " s");
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
