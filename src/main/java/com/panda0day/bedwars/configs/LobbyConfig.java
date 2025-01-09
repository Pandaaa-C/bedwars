package com.panda0day.bedwars.configs;

import com.panda0day.bedwars.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LobbyConfig implements Config {
    private final File file;
    private FileConfiguration fileConfiguration;

    public LobbyConfig(String fileName) {
        this.file = new File(Main.getInstance().getDataFolder(), fileName);

        this.createFile();
        this.createDefaults();
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch(IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void createDefaults() {
        fileConfiguration.addDefault("world", "lobby");
        fileConfiguration.addDefault("x", 0.5);
        fileConfiguration.addDefault("y", 102.0);
        fileConfiguration.addDefault("z", 0.5);
        fileConfiguration.addDefault("yaw", 180.0);
        fileConfiguration.addDefault("pitch", 0.0);

        fileConfiguration.options().copyDefaults(true);
        save();
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public String getLobbyName() {
        return fileConfiguration.getString("world");
    }
}
