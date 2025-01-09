package com.panda0day.bedwars.configs;

import com.panda0day.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpawnablesConfig implements Config {
    private final File file;
    private FileConfiguration fileConfiguration;

    public SpawnablesConfig(String fileName) {
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
        fileConfiguration.addDefault("brick.locations", List.of(
                Map.of("world", "game", "x", 74.5, "y", 65.25, "z", -29.5),
                Map.of("world", "game", "x", 74.5, "y", 65.25, "z", 30.5),
                Map.of("world", "game", "x", 29.5, "y", 65.25, "z", 73.5),
                Map.of("world", "game", "x", -30.5, "y", 65.25, "z", 73.5),
                Map.of("world", "game", "x", -73.5, "y", 65.25, "z", 28.5),
                Map.of("world", "game", "x", -73.5, "y", 65.25, "z", -31.5),
                Map.of("world", "game", "x", -28.5, "y", 65.25, "z", -74.5),
                Map.of("world", "game", "x", 31.5, "y", 65.25, "z", -74.5)
        ));
        fileConfiguration.addDefault("iron.locations", List.of(
                Map.of("world", "game", "x", 30.5, "y", 65.25, "z", -30.5),
                Map.of("world", "game", "x", 30.5, "y", 65.25, "z", 29.5),
                Map.of("world", "game", "x", -29.5, "y", 65.25, "z", 29.5),
                Map.of("world", "game", "x", -29.5, "y", 65.25, "z", -30.5)
        ));
        fileConfiguration.addDefault("gold.locations", List.of(
                Map.of("world", "game", "x", -9.5, "y", 66.25, "z", 9.5),
                Map.of("world", "game", "x", -9.5, "y", 66.25, "z", -10.5),
                Map.of("world", "game", "x", 10.5, "y", 66.25, "z", -10.5),
                Map.of("world", "game", "x", 10.5, "y", 66.25, "z", 9.5)
        ));
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

    public List<Location> getLocations(String path) {
        List<?> locations = fileConfiguration.getList(path);
        List<Location> locationList = new LinkedList<>();

        if (locations != null) {
            for (Object obj : locations) {
                if (obj instanceof Map) {
                    Map<?, ?> locationData = (Map<?, ?>) obj;

                    String worldName = (String) locationData.get("world");
                    World world = Main.getInstance().getServer().getWorld(worldName);

                    if (world != null) {
                        double x = ((Number) locationData.get("x")).doubleValue();
                        double y = ((Number) locationData.get("y")).doubleValue();
                        double z = ((Number) locationData.get("z")).doubleValue();
                        Location location = new Location(world, x, y, z);

                        locationList.add(location);
                    }
                }
            }
        }

        return locationList;
    }

}
