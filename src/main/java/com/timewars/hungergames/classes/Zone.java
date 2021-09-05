package com.timewars.hungergames.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;

public class Zone {
    Location center;
    int radius;
    WorldBorder brd;

    public Zone(Location c, int r) {
        center = c;
        radius = r;
        System.out.println();
        try {
            brd = Bukkit.getWorld("world").getWorldBorder();
        } catch (NullPointerException e) {
            System.out.println("there is no border lol");
        }
    }

    public void createZone() {
        brd.setCenter(center);
        brd.setSize(2 * radius);
        brd.setDamageAmount(5);
        brd.setDamageBuffer(0);
        brd.setWarningDistance(0);
        brd.setWarningTime(0);
    }

    public void startMove(int time) {
        brd.setSize(5, time);
    }
}
