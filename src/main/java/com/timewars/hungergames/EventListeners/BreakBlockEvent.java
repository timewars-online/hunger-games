package com.timewars.hungergames.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
