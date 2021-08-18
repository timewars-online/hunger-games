package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HungerGames.game.playerDisconnected(player);
    }

}
