package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import com.timewars.hungergames.classes.mPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HungerGames.game.playerJoined(player);

        if(!HungerGames.game.getSpawnpoints().isEmpty()) {
            player.teleport(HungerGames.game.getSpawnpoints().pop());
        }
        if(!HungerGames.game.isGameStarted && HungerGames.game.getPlayers().size() == HungerGames.game.getMAXPLAYERS()) {
            new Thread(() -> HungerGames.game.preparingGame()).start();
        }

        mPlayer mplayer = HungerGames.game.getPlayerShell(player);
        HungerGames.sidebar.startUpdatingSideBar(mplayer);
    }

}
