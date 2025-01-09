package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Config {
    private final File configFile;
    private final YamlConfiguration yamlConfig;

    public Config(JavaPlugin plugin) {
        this.configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Optional: To copy default config from JAR if you want
        }

        this.yamlConfig = YamlConfiguration.loadConfiguration(configFile);

        setDefaults();
    }

    private void setDefaults() {
        if (!yamlConfig.contains("main.prefix")) {
            yamlConfig.set("main.prefix", "&6BedWars &8» &f");
        }

        setupDefaultResourceSpawns();

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
        String prefix = yamlConfig.getString("main.prefix", "&6BedWars &8» &f");
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public List<Location> getLocationsFromConfig(String path) {
        List<Location> locations = new ArrayList<>();
        List<Map<?, ?>> rawLocations = yamlConfig.getMapList(path);

        if (rawLocations != null) {
            for (Map<?, ?> rawLocation : rawLocations) {
                String worldName = (String) rawLocation.get("world");
                double x = rawLocation.containsKey("x") ? ((Number) rawLocation.get("x")).doubleValue() : 0.0;
                double y = rawLocation.containsKey("y") ? ((Number) rawLocation.get("y")).doubleValue() : 0.0;
                double z = rawLocation.containsKey("z") ? ((Number) rawLocation.get("z")).doubleValue() : 0.0;

                if (worldName != null && Bukkit.getWorld(worldName) != null) {
                    locations.add(new Location(Bukkit.getWorld(worldName), x, y, z));
                }
            }
        }

        return locations;
    }

    private void setupDefaultResourceSpawns() {

    }
}
