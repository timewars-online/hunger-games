package com.timewars.hungergames.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private static File file; //real file which we convert to customFile
    private static FileConfiguration customFile;

    public static void setup()
    {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DataReminder").getDataFolder(), "items.yml");

        if(!file.exists())
        {
            System.out.println("Trying to Create New File");
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println(" Execption :(" + e.getMessage());
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void save()
    {
        System.out.println("Trying to Save data..");
        try
        {
            customFile.save(file);
            System.out.println("Saved!");
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save CustomFile :(");
        }
    }

    public static void reload()
    {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getCustomFile() {
        return customFile;
    }

    public static void setCustomFile(FileConfiguration customFile) {
        CustomConfig.customFile = customFile;
    }
}
