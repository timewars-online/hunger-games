package com.timewars.hungergames;

//import fr.minuskube.netherboard.Netherboard;
//import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Sidebar {

    private HungerGames hungerGamesPlugin;
    private int taskID;

    public Sidebar(HungerGames hungerGamesPlugin) {
        this.hungerGamesPlugin = hungerGamesPlugin;
    }

    public void createBoard(Player player) {
//        BPlayerBoard board = Netherboard.instance().createBoard(player,
//                ChatColor.AQUA.toString() + ChatColor.ITALIC + "TimeWars");
//
//
//        board.setAll(
//                ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players"  ,
//                Bukkit.getOnlinePlayers().size() + " alive",
//                "   ",
//                ChatColor.GOLD + ChatColor.BOLD.toString() + "Stats",
//                "Kills: " + ChatColor.GREEN + player.getStatistic(Statistic.PLAYER_KILLS),
//                "  " ,
//                ChatColor.YELLOW + ChatColor.BOLD.toString() + "Time: " +
//                        ChatColor.RESET +
//                        (getGameTime())
//        );


        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("HubScoreboard-1", "dummy",
                ChatColor.AQUA.toString() + ChatColor.ITALIC + "TimeWars");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore(ChatColor.BLUE + "----------------");
        score.setScore(7);

        Score score1 = obj.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players");
        score1.setScore(6);

        Score score2 = obj.getScore(Bukkit.getOnlinePlayers().size() + " alive(NO)");
        score2.setScore(5); //TODO change this to another way

        Score score3 = obj.getScore("   ");
        score3.setScore(4);

        Score score4 = obj.getScore(ChatColor.GOLD + ChatColor.BOLD.toString() + "Stats");
        score4.setScore(3);

        Score score5 = obj.getScore("Kills: " + ChatColor.GREEN + player.getStatistic(Statistic.PLAYER_KILLS));
        score5.setScore(2); //TODO change this to another way

        Score score6 = obj.getScore("  " );
        score6.setScore(1);

        Score score7 = obj.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Time: " +
                        ChatColor.RESET + (getGameTime()));
        score7.setScore(0);

        player.setScoreboard(board);
    }

    public String getGameTime()
    {
        long seconds = Math.abs(Bukkit.getWorld("world").getTime() - HungerGames.game.getTimeStarted()) / 20;

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
