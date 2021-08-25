package com.timewars.hungergames.classes;

import org.bukkit.entity.Player;

public class mPlayer {
    public Player player;

    public mPlayer(Player p) {
        player = p;
    }

    public boolean isShell(Player p) {
        return p == player;
    }

}
