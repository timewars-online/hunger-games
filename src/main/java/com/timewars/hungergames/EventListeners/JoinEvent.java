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

        if(!HungerGames.game.getLoc().isEmpty()) {
            player.teleport(HungerGames.game.getLoc().element());
        }
        if(HungerGames.game.getPlayers().size() == HungerGames.game.getMAXPLAYERS()) {
            new Thread(() -> HungerGames.game.preparingGame()).start();
        }

        HungerGames.sidebar.startUpdatingSideBar(player);
    }

    void gameNotStarted(Player player) {
        player.setWalkSpeed(0);
        player.setFlySpeed(0);
    }

}
