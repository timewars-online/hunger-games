package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinEvent implements Listener {

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HungerGames.game.playerJoined(player);

        if(!HungerGames.game.getLoc().isEmpty()) {
            player.teleport(HungerGames.game.getLoc().element());
        }
        if(!HungerGames.game.isGameStarted && HungerGames.game.getPlayers().size() == HungerGames.game.getMAXPLAYERS()) {
            new Thread(() -> HungerGames.game.preparingGame()).start();
        }

        HungerGames.sidebar.startUpdatingSideBar(player);
    }

}
