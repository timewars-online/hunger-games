package com.timewars.hungergames;

import com.timewars.hungergames.classes.myItem;
import com.timewars.hungergames.files.CustomConfig;
import com.timewars.hungergames.files.ItemsOperations;
import jdk.internal.net.http.common.Pair;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    public LinkedList<Location> loc; //потом поменять
    LinkedList<Boolean> used;
    public LinkedList<mPlayer> players;
    public int MAXPLAYERS, MAXARTCHANCE;
    public boolean isGameStarted;
    public String mapname;
    public LinkedList< Pair<Integer, Material>> artifacts;

    ItemsOperations itemsOperations;
    private ArrayList<myItem> items;
    private int itemCasinoCounter;

    Game(String mapname) {
        players = new LinkedList<>();
        loc = new LinkedList<>();
        MAXPLAYERS = 2; //подтянуть из базы
        isGameStarted = false;
        this.mapname = mapname;

        itemsOperations = new ItemsOperations();
        items = new ArrayList<>();
        itemCasinoCounter = 0;

        readChests();
        readSpawnSpots();
        //readArts();
        PrepareCasinoSpins();
    }

    public void readChests() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/data?autoReconnect=true&useSSL=false", "root", "");) {
            Statement statement = connection.createStatement();
            Formatter f = new Formatter();
            ResultSet chests = statement.executeQuery(f.format("SELECT xcord, ycord, zcord FROM chests WHERE mapname = '%s'", mapname).toString());
            while (chests.next()) {
                Location chest = new Location(Bukkit.getServer().getWorld("world"), chests.getInt(1), chests.getInt(2), chests.getInt(3));
                if (chest.getBlock().getType() == Material.CHEST) {
                    FillChest(chest.getBlock());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void readSpawnSpots() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/data?autoReconnect=true&useSSL=false", "root", "");) {
            Statement statement = connection.createStatement();
            Formatter f = new Formatter();
            ResultSet spawnspots = statement.executeQuery(f.format("SELECT xcord, ycord, zcord FROM spawnspot WHERE mapname = '%s'", mapname).toString());
            while (spawnspots.next()) {
                loc.add(new Location(Bukkit.getServer().getWorld("world"), spawnspots.getInt(1), spawnspots.getInt(2), spawnspots.getInt(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void readArts() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/data?autoReconnect=true&useSSL=false", "root", "");) {
            Statement statement = connection.createStatement();
            MAXARTCHANCE = 0;
            Formatter f = new Formatter();
            ResultSet arts = statement.executeQuery("SELECT art, tic FROM art");
            while(arts.next()) {
                MAXARTCHANCE += arts.getInt(2);
                artifacts.add(new Pair<Integer, Material>(MAXARTCHANCE, Material.getMaterial(arts.getString(1))));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void PrepareCasinoSpins()
    {
        for ( myItem item : itemsOperations.getItems())
        {
            itemCasinoCounter += item.getProbability();

            myItem newItem = new myItem(item.getItemStack(), itemCasinoCounter);
            items.add(newItem);
        }
    }

    public void FillChest(Block chestBlock)
    {
        Chest chest = (Chest) chestBlock;
        chest.getInventory().clear();

        for(int i = 0; i < 4 + Math.random() * 4; i++)
        {
            int ticketNum = (int) (Math.random() * itemCasinoCounter); //new Random().nextInt(itemCasinoCounter);
            ItemStack spawnedItem = null;

            for (myItem item : items)
            {
                if (ticketNum < item.getProbability())
                {
                    spawnedItem = new ItemStack(item.getItemStack());
                    break;
                }
            }

            if (spawnedItem != null)
            {
                int position;
                do
                {
                    position = new Random().nextInt(chest.getInventory().getSize());
                }
                while (chest.getInventory().getItem(position) != null);

                chest.getInventory().setItem(position, spawnedItem);
            }
        }
    }

    public void fillChest(Block chest) {
        for(int i = 0; i < 4 + Math.random() * 4; i++) {
            int ticketNum = (int) (Math.random() * MAXARTCHANCE);
            Material spawnedItem = null;
            for (Pair<Integer, Material> it : artifacts) {
                if (ticketNum < it.first) {
                    spawnedItem = it.second;
                }
            }
            ItemStack item = new ItemStack(spawnedItem);
            ((Chest) chest).getInventory().addItem(item);
        }
    }

    public void playerJoined(Player player) {
        mPlayer playerShell = new mPlayer(player);
        players.add(playerShell);
    }

    public mPlayer getPlayerShell(Player player) {
        for(mPlayer p : players) {
            if(p.isShell(player)) return p;
        }
        return null;
    }

    public void playerDisconnected(Player player) {
        players.removeIf(p -> p.isShell(player));
    }

    public void preparingGame() {
        int countdown;
        for(countdown = 10; countdown >= 0 & players.size() == MAXPLAYERS; countdown--) {
            for(mPlayer player : players) {
                player.player.sendTitle(ChatColor.DARK_PURPLE + "" + countdown, "", 2, 6, 2);
            }
            try {
                Thread.sleep(1000);
            } catch (Throwable e) {
                System.out.println("Error occurred in Game. Please check it. InterruptedError");
            }
        }
        if(players.size() == MAXPLAYERS) {
            for(mPlayer player : players) {
                player.player.sendTitle(ChatColor.GREEN + "Game started!", "", 2, 10, 2);
            }
            isGameStarted = true;
        }
    }

    public void startGame() {

    }

    class mPlayer{
        private Player player;
        private long lastHeatedTime, lastHealUsedTime, lastFeedUsedTime, remainingHeal, remainingFeed;

        mPlayer(Player p) {
            player = p;
            lastHeatedTime = -1000000000;
        }

        public boolean isShell(Player p) {
            return p == player;
        }

    }

}
