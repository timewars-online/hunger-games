package com.timewars.hungergames;

import com.timewars.hungergames.classes.mPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Sidebar {

    private HungerGames hungerGamesPlugin;
    private int taskID;

    public Sidebar(HungerGames hungerGamesPlugin) {
        this.hungerGamesPlugin = hungerGamesPlugin;
    }

    public void createBoard(mPlayer mplayer) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective obj = board.registerNewObjective("HubScoreboard-1", "dummy",
                ChatColor.AQUA.toString() + ChatColor.ITALIC + "TimeWars");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = obj.getScore(ChatColor.BLUE + "----------------");
        score.setScore(7);

        Score score1 = obj.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players");
        score1.setScore(6);

        Score score2 = obj.getScore(HungerGames.game.getPlayers().size() + " alive");
        score2.setScore(5);

        Score score3 = obj.getScore("   ");
        score3.setScore(4);

        Score score4 = obj.getScore(ChatColor.GOLD + ChatColor.BOLD.toString() + "Stats");
        score4.setScore(3);

        Score score5 = obj.getScore("Kills: " + ChatColor.GREEN + mplayer.getKills());
        score5.setScore(2);

        Score score6 = obj.getScore("  " );
        score6.setScore(1);

        Score score7 = obj.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Time: " +
                        ChatColor.RESET + (getGameTime()));
        score7.setScore(0);

        mplayer.player.setScoreboard(board);
    }

    public String getGameTime()
    {
        long seconds = Math.abs(Bukkit.getWorld("world").getTime() - HungerGames.game.getTimeStarted()) / 20;

        String time = null;

        long minutes = seconds/60;

        if (minutes > 0 ) return (minutes + " min " + (seconds - minutes*60) + " s");

        return (seconds + " s");
    }

    public void startUpdatingSideBar(mPlayer player) {

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(hungerGamesPlugin, new Runnable() {
            @Override
            public void run() {
                createBoard(player);
            }
        }, 0,20);
    }
}
