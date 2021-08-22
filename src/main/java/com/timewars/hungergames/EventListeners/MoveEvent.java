package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if(!HungerGames.game.isGameStarted) {
            //event.setCancelled(true);
        }
    }

}
