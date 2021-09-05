package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.net.ssl.HandshakeCompletedEvent;

public class MoveEvent implements Listener {

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if(!HungerGames.game.isGameStarted) {
            if (event.getFrom().getX() != event.getTo().getX() ||
                    event.getFrom().getZ() != event.getTo().getZ()) {
                event.setCancelled(true);
            }
        }
        else HandlerList.unregisterAll(this);
    }

}
