package com.timewars.hungergames.EventListeners;

import com.timewars.hungergames.HungerGames;
import com.timewars.hungergames.classes.mPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class DeathEvent implements Listener {

    @EventHandler
    void onDamage(EntityDamageEvent event) {
        Entity damagedEnt = event.getEntity();
        if (!(event instanceof EntityDamageByEntityEvent) && damagedEnt instanceof Player && ((Player) damagedEnt).getHealth() - event.getDamage() <= 0) {
            event.setCancelled(true);
            Player player = (Player) damagedEnt;
            mPlayer mplayer = HungerGames.game.getPlayerShell(player);
            Location dLocation = player.getLocation();
            for (ItemStack item : player.getInventory()) {
                if (item != null) {
                    if (new Random().nextInt(10) <= 1) { //20 %
                        if (item.getAmount() > 1) {
                            item.setAmount(item.getAmount() / 2);
                            player.getWorld().dropItemNaturally(dLocation, item);
                        }
                    } else player.getWorld().dropItemNaturally(dLocation, item);
                }
            }
            player.getInventory().clear();
            player.getWorld().strikeLightningEffect(dLocation);
            player.setGameMode(GameMode.SPECTATOR);

            HungerGames.game.getPlayers().remove(mplayer);

            player.sendTitle(ChatColor.RED + "You was killed by the World!", ChatColor.DARK_AQUA + "Don't worry, you will win next time", 5, 25, 5);
            HungerGames.game.checkAlivePlayers();
        }
    }
}
