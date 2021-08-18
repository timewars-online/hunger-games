package com.timewars.hungergames.EventListeners;

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

public class DeathEvent implements Listener {

    @EventHandler
    void onDamage(EntityDamageByBlockEvent event) {
            Entity damagedEnt = event.getEntity();
            if(damagedEnt instanceof Player & ((Player) damagedEnt).getHealth() - event.getDamage() <= 0) {
                event.setCancelled(true);
                Player player = (Player) damagedEnt;
                Location dLocation = player.getLocation();
                for(ItemStack item : player.getInventory()) {
                    if(item != null) {
                        player.getInventory().remove(item);
                        player.getWorld().dropItemNaturally(dLocation, item);
                    }
                }
                player.getWorld().strikeLightningEffect(dLocation);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle( ChatColor.RED + "You was killed by the World!", ChatColor.DARK_AQUA + "Don't worry, you will win next time", 5, 25, 5);
            }
    }
}
