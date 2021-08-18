package com.timewars.hungergames.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class KillEvent implements Listener {

    @EventHandler
    void onDeath(EntityDamageByEntityEvent event) {
        Entity damagedEnt = event.getEntity();
        Entity killerEnt = event.getDamager();
        if(damagedEnt instanceof Player & killerEnt instanceof Player & ((Player) damagedEnt).getHealth() - event.getDamage() <= 0) {
            event.setCancelled(true);
            Player player = (Player) damagedEnt;
            player.setCanPickupItems(false);
            Player killer = (Player) killerEnt;
            player.setInvisible(true);
            Location dLocation = player.getLocation();
            for(ItemStack item : player.getInventory()) {
                if(item != null) {
                    player.getInventory().remove(item);
                    player.getWorld().dropItemNaturally(dLocation, item);
                }
            }
            player.getWorld().strikeLightningEffect(dLocation);
            player.setGameMode(GameMode.SPECTATOR);
            player.setHealth(player.getHealthScale());
            killer.setHealth(killer.getHealthScale());
            killer.sendTitle(ChatColor.DARK_PURPLE + "You killed " + player.getName() + "!", ChatColor.LIGHT_PURPLE + "Your health was restored!", 5, 25, 5);
            player.sendTitle( ChatColor.RED + "You was killed by " + killer.getName() + "!", ChatColor.DARK_AQUA + "Don't worry, you will win next time", 5, 25, 5);
        }
    }

}