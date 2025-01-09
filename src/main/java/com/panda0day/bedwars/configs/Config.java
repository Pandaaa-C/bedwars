package com.panda0day.bedwars.configs;

import org.bukkit.configuration.file.FileConfiguration;

public interface Config {
    void createFile();
    void createDefaults();
    void save();
    void reload();
    FileConfiguration getFileConfiguration();
}
