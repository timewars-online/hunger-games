package com.timewars.hungergames;

import com.timewars.hungergames.EventListeners.*;

import com.timewars.hungergames.classes.MainCommand;
import com.timewars.hungergames.classes.myItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class HungerGames extends JavaPlugin {

    public static Game game;
    public static Sidebar sidebar;

    {
        ConfigurationSerialization.registerClass(ItemStack.class);
        ConfigurationSerialization.registerClass(myItem.class);
    }

    @Override
    public void onEnable() {

        System.out.println("HungerGames Plugin Starts...");

        MainCommand hgCommands = new MainCommand();

        getCommand("hg").setExecutor(hgCommands);

        getServer().getPluginManager().registerEvents(new BreakBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new KillEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);

        game = new Game("world1", this);
        sidebar = new Sidebar(this);
    }
}
