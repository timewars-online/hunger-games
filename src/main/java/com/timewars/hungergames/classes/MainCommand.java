package com.timewars.hungergames.classes;

import com.timewars.hungergames.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainCommand implements TabExecutor {
    private HashMap<String, TabExecutor> subCommands;

    public MainCommand() {
        this.subCommands = new HashMap<String, TabExecutor>();
    }

    public void registerCommand(String name, TabExecutor executor) {
        subCommands.put(name, executor);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!player.hasPermission(Permissions.PLAYER_USE_MAIN_COMMAND)) return false;
        if (args.length > 0 && subCommands.containsKey(args[0])) {
            String newLabel = args[0];
            String[] newArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                newArgs[i - 1] = args[i];
            }

            return subCommands.get(newLabel).onCommand(sender, command, newLabel, newArgs);
        }else{
            sender.sendMessage(ChatColor.RED + "Unknown command, please try again");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return new ArrayList<>();
        Player player = (Player) sender;
        if (!player.hasPermission(Permissions.PLAYER_USE_MAIN_COMMAND)) return new ArrayList<>();
        if (args.length == 1) {
            ArrayList<String> availableCommands = new ArrayList<String>();
            for (String commandName : subCommands.keySet()) {
                if (commandName.contains(args[0]))
                    availableCommands.add(commandName);
            }
            return availableCommands;
        } else if (args.length > 1 && subCommands.containsKey(args[0])) {
            String newLabel = args[0];

            String[] newArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                newArgs[i - 1] = args[i];
            }

            return subCommands.get(newLabel).onTabComplete(sender, command, newLabel, newArgs);
        }

        return new ArrayList<String>();
    }


}
