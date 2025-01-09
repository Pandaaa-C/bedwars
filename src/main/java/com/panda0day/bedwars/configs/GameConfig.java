package com.panda0day.bedwars.configs;

import com.panda0day.bedwars.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GameConfig implements Config {
    private final File file;
    private FileConfiguration fileConfiguration;

    public GameConfig(String fileName) {
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
        // TODO: Set default game config files
        fileConfiguration.addDefault("map_name", "Tropical");
        fileConfiguration.addDefault("map_world", "game");
        fileConfiguration.addDefault("countdown_time", 60);
        fileConfiguration.addDefault("minimum_players", 2);
        fileConfiguration.addDefault("maximum_players", 8);
        fileConfiguration.addDefault("maximum_per_team", 1);
        fileConfiguration.addDefault("teams", 8);

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

    public String getWorldName() {
        return fileConfiguration.getString("map_world");
    }
    public String getMapName() {
        return fileConfiguration.getString("map_name");
    }

    public int getCountdownTime() {
        return fileConfiguration.getInt("countdown_time");
    }

    public int getMinimumPlayers() {
        return fileConfiguration.getInt("minimum_players");
    }

    public int getMaximumPlayers() {
        return fileConfiguration.getInt("maximum_players");
    }

    public int getMaximumPerTeam() {
        return fileConfiguration.getInt("maximum_per_team");
    }
}
