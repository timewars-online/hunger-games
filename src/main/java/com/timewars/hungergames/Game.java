package com.timewars.hungergames;

import com.timewars.hungergames.classes.myItem;
import com.timewars.hungergames.files.ItemsOperations;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.sql.*;
import java.util.*;

public class Game {
    public LinkedList<Location> loc; //change later
    LinkedList<Boolean> used;
    public LinkedList<mPlayer> players;
    public int MAXPLAYERS;


    public boolean isGameStarted;
    private String mapname;

    private ItemsOperations itemsOperations;
    private ArrayList<myItem> items;
    private ArrayList<myItem> itemsSuperChest;
    private int itemCasinoCounter;
    private int itemSuperCasinoCounter;

    Game(String mapname) {
        players = new LinkedList<>();
        loc = new LinkedList<>();
        MAXPLAYERS = 1; //gain from api
        isGameStarted = false;
        this.mapname = mapname;

        itemsOperations = new ItemsOperations();
        items = new ArrayList<>();
        itemsSuperChest = new ArrayList<>();
        itemCasinoCounter = 0;
        itemSuperCasinoCounter = 0;

        prepareItems();
        readAndFillChests("chests");
        readAndFillChests("super_chests");
        readAndFillChests("random_super_chests");
        readSpawnSpots();
    }

    public void readAndFillChests(String tableName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/data?autoReconnect=true&useSSL=false", "root", "")) {
            Statement statement = connection.createStatement();
            Formatter f = new Formatter();
            ResultSet chests = statement.executeQuery(f.format("SELECT xcord, ycord, zcord FROM %s WHERE mapname = '%s'", tableName, mapname).toString());
            while (chests.next()) {
                Location chestLocation = new Location(Bukkit.getServer().getWorld("world"), chests.getInt(1), chests.getInt(2), chests.getInt(3));
                if (chestLocation.getBlock().getType() == Material.CHEST && tableName.equals("chests")) {
                    Inventory inventory = ((Chest) chestLocation.getBlock().getState()).getInventory();
                    fillChest(inventory, items, itemCasinoCounter);
                } else if (chestLocation.getBlock().getType() == Material.SHULKER_BOX && tableName.equals("super_chests")) {
                    Inventory inventory = ((ShulkerBox) chestLocation.getBlock().getState()).getInventory();
                    fillChest(inventory, itemsSuperChest, itemSuperCasinoCounter);
                }
                else if (chestLocation.getBlock().getType() == Material.WHITE_SHULKER_BOX && tableName.equals("random_super_chests")) {
                    if ( new Random().nextInt(4) == 0 ) chestLocation.getBlock().setType(Material.AIR); // or put sign with some text
                    else {
                        Inventory inventory = ((ShulkerBox) chestLocation.getBlock().getState()).getInventory();
                        fillChest(inventory, itemsSuperChest, itemSuperCasinoCounter);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void readSpawnSpots() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/data?autoReconnect=true&useSSL=false", "root", "")) {
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

    public void prepareItems() {
        int maxProbability = 0;
        for (myItem item : itemsOperations.getItems()) {
            maxProbability = Math.max(maxProbability, item.getProbability());

            itemCasinoCounter += item.getProbability();

            myItem newItem = new myItem(item.getItemStack(), itemCasinoCounter);
            items.add(newItem);
        }

        for (myItem item : itemsOperations.getItems()) {
            int itemProbability = maxProbability - item.getProbability();

            itemSuperCasinoCounter += itemProbability;

            myItem newItem = new myItem(item.getItemStack(), itemSuperCasinoCounter);
            itemsSuperChest.add(newItem);
        }
    }

    public ItemStack enchantItem(ItemStack item) {
        List<Enchantment> possible = new ArrayList<>();

        for (Enchantment ench : Enchantment.values()) {
            if (ench.canEnchantItem(item))
                possible.add(ench);
        }

        if (possible.size() > 0) {
            Enchantment chosen = possible.get(new Random().nextInt(possible.size()));
            item.addEnchantment(chosen, 1 + (int) (Math.random() * ((chosen.getMaxLevel() - 1) + 1)));

            if (new Random().nextInt(10) == 1 ) { // 10%
                chosen = possible.get(new Random().nextInt(possible.size()));
                item.addEnchantment(chosen, 1 + (int) (Math.random() * ((chosen.getMaxLevel() - 1) + 1)));
            }
        }
        return item;
    }

    public void fillChest(Inventory chestInventory, ArrayList<myItem> items, int counter) {
        chestInventory.clear();

        for (int i = 0; i < 4 + Math.random() * 4; i++) {
            int ticketNum = (int) (Math.random() * counter); //new Random().nextInt(itemCasinoCounter);
            ItemStack spawnedItem = null;

            for (myItem item : items) {
                if (ticketNum < item.getProbability()) {
                    spawnedItem = new ItemStack(item.getItemStack());
                    break;
                }
            }

            if (spawnedItem != null) {
                int position;
                do {
                    position = new Random().nextInt(chestInventory.getSize());
                }
                while (chestInventory.getItem(position) != null);

                if (new Random().nextInt(10) == 0) {
                    System.out.println("trying to enchant");
                    spawnedItem = enchantItem(spawnedItem);
                }

                chestInventory.setItem(position, spawnedItem);
            }
        }
    }

    public void playerJoined(Player player) {
        mPlayer playerShell = new mPlayer(player);
        players.add(playerShell);
    }

    public mPlayer getPlayerShell(Player player) {
        for (mPlayer p : players) {
            if (p.isShell(player)) return p;
        }
        return null;
    }

    public void playerDisconnected(Player player) {
        players.removeIf(p -> p.isShell(player));
    }

    public void preparingGame() {
        int countdown;
        for (countdown = 1; countdown >= 0 & players.size() == MAXPLAYERS; countdown--) {
            for (mPlayer player : players) {
                player.player.sendTitle(ChatColor.DARK_PURPLE + "" + countdown, "", 2, 6, 2);
            }
            try {
                Thread.sleep(1000);
            } catch (Throwable e) {
                System.out.println("Error occurred in Game. Please check it. InterruptedError");
            }
        }
        if (players.size() == MAXPLAYERS) {
            for (mPlayer player : players) {
                player.player.sendTitle(ChatColor.GREEN + "Game started!", "", 2, 10, 2);
            }
            isGameStarted = true;
        }
    }

    public void startGame() {


    }


    class mPlayer {
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
