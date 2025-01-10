package com.panda0day.bedwars.configs;

import com.panda0day.bedwars.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DatabaseConfig implements Config {
    private final File file;
    private FileConfiguration fileConfiguration;

    public DatabaseConfig(String fileName) {
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
        fileConfiguration.addDefault("host", "localhost");
        fileConfiguration.addDefault("username", "root");
        fileConfiguration.addDefault("password", "");
        fileConfiguration.addDefault("port", 3306);
        fileConfiguration.addDefault("database", "bedwars");

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

    public String getHost() { return fileConfiguration.getString("host"); }
    public String getUsername() { return fileConfiguration.getString("username"); }
    public String getPassword() { return fileConfiguration.getString("password"); }
    public int getPort() { return fileConfiguration.getInt("port"); }
    public String getDatabase() { return fileConfiguration.getString("database"); }
}
