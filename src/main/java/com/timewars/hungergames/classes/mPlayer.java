package com.timewars.hungergames.classes;

import org.bukkit.entity.Player;

public class mPlayer{
    public Player player;

    private int kills;
    private int assists;

    public mPlayer(Player p) {
        player = p;
    }

    public boolean isShell(Player p) {
        return p == player;
    }

    public void addKill(){
        kills+=1;
    }

    public void addAssist(){
        assists+=1;
    }

    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }
}
