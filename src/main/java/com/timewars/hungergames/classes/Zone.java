package com.timewars.hungergames.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;

public class Zone {
    Location center;
    int radius;
    int timeToShrink;
    int endSize;
    WorldBorder brd;

    public Zone(Location c, int r, int t, int endSize) {
        center = c;
        radius = r;
        timeToShrink = t;
        this.endSize = endSize;
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

    public void startMove() {
        brd.setSize(endSize, timeToShrink);
    }
}
