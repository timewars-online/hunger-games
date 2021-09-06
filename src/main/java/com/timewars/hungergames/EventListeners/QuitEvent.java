package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import com.timewars.hungergames.classes.LobbyBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        LobbyBoard board = new LobbyBoard(player.getUniqueId());
        if (board.hasID()) board.stop();
        HungerGames.game.playerDisconnected(player);

        HungerGames.game.checkAlivePlayers();

        //TODO kill game if 0 players
    }

}
