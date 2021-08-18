package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HungerGames.game.playerJoined(player);
        if(!HungerGames.game.loc.isEmpty()) {
            player.teleport(HungerGames.game.loc.element());
        }
        if(HungerGames.game.players.size() == HungerGames.game.MAXPLAYERS) {
            new Thread(() -> HungerGames.game.preparingGame()).start();
        }
    }

}
