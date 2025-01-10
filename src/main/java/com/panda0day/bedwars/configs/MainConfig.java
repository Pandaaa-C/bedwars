package com.panda0day.bedwars.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainConfig {
    private final File configFile;
    private final YamlConfiguration yamlConfig;

    public MainConfig(JavaPlugin plugin) {
        this.configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Optional: To copy default config from JAR if you want
        }

        this.yamlConfig = YamlConfiguration.loadConfiguration(configFile);

        setDefaults();
    }

    private void setDefaults() {
        if (!yamlConfig.contains("prefix")) {
            yamlConfig.set("prefix", "&6BedWars &8» &f");
        }

        if (!yamlConfig.contains("lobby_world")) {
            yamlConfig.set("lobby_world", "lobby");
        }

        saveConfig();
    }

    public void saveConfig() {
        try {
            yamlConfig.save(configFile);
        } catch (IOException e) {
            System.out.println("Failed to save config.yml");
            System.out.println(e.getMessage());
        }
    }

    public String getPrefix() {
        String prefix = yamlConfig.getString("prefix", "&6BedWars &8» &f");
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public String getLobbyWorld() {
        return yamlConfig.getString("lobby_world", "lobby");
    }
}
