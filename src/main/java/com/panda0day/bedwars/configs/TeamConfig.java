package com.panda0day.bedwars.configs;

import com.panda0day.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TeamConfig implements Config {
    private final File file;
    private FileConfiguration fileConfiguration;

    public TeamConfig(String fileName) {
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
        Map<String, Object> whiteTeam = new LinkedHashMap<>();
        whiteTeam.put("color", ChatColor.WHITE.toString());
        whiteTeam.put("name", "White");
        whiteTeam.put("team_name", "Team White");
        whiteTeam.put("material", "WHITE_WOOL");

        Map<String, Object> whiteTeamSpawn = new LinkedHashMap<>();
        whiteTeamSpawn.put("x", 30.5);
        whiteTeamSpawn.put("y", 65.5);
        whiteTeamSpawn.put("z", 66.5);
        whiteTeamSpawn.put("yaw", -180);
        whiteTeamSpawn.put("pitch", -1.0);
        whiteTeam.put("spawn", whiteTeamSpawn);

        Map<String, Object> whiteShopSpawn = new LinkedHashMap<>();
        whiteShopSpawn.put("x", 26.5);
        whiteShopSpawn.put("y", 65.0);
        whiteShopSpawn.put("z", 70.5);
        whiteShopSpawn.put("yaw", -90.0);
        whiteShopSpawn.put("pitch", 0.0);
        whiteTeam.put("shop", whiteShopSpawn);

        fileConfiguration.addDefault("white", whiteTeam);

        Map<String, Object> blackTeam = new LinkedHashMap<>();
        blackTeam.put("color", ChatColor.BLACK.toString());
        blackTeam.put("name", "Black");
        blackTeam.put("team_name", "Team Black");
        blackTeam.put("material", "BLACK_WOOL");

        Map<String, Object> blackTeamSpawn = new LinkedHashMap<>();
        blackTeamSpawn.put("x", -30.5);
        blackTeamSpawn.put("y", 65.5);
        blackTeamSpawn.put("z", 66.5);
        blackTeamSpawn.put("yaw", -180);
        blackTeamSpawn.put("pitch", -1.0);
        blackTeam.put("spawn", blackTeamSpawn);

        Map<String, Object> blackShopSpawn = new LinkedHashMap<>();
        blackShopSpawn.put("x", -33.5);
        blackShopSpawn.put("y", 65.0);
        blackShopSpawn.put("z", 70.5);
        blackShopSpawn.put("yaw", -90.0);
        blackShopSpawn.put("pitch", 0.0);
        blackTeam.put("shop", blackShopSpawn);

        fileConfiguration.addDefault("black", blackTeam);

        Map<String, Object> redTeam = new LinkedHashMap<>();
        redTeam.put("color", ChatColor.DARK_RED.toString());
        redTeam.put("name", "Red");
        redTeam.put("team_name", "Team Red");
        redTeam.put("material", "RED_WOOL");

        Map<String, Object> redTeamSpawn = new LinkedHashMap<>();
        redTeamSpawn.put("x", -29.5);
        redTeamSpawn.put("y", 65.5);
        redTeamSpawn.put("z", -67.5);
        redTeamSpawn.put("yaw", 0.0);
        redTeamSpawn.put("pitch", 0.0);
        redTeam.put("spawn", redTeamSpawn);

        Map<String, Object> redShopSpawn = new LinkedHashMap<>();
        redShopSpawn.put("x", -25.5);
        redShopSpawn.put("y", 65.0);
        redShopSpawn.put("z", -71.5);
        redShopSpawn.put("yaw", 90.0);
        redShopSpawn.put("pitch", 0.0);
        redTeam.put("shop", redShopSpawn);

        fileConfiguration.addDefault("red", redTeam);

        Map<String, Object> orangeTeam = new LinkedHashMap<>();
        orangeTeam.put("color", ChatColor.GOLD.toString());
        orangeTeam.put("name", "Orange");
        orangeTeam.put("team_name", "Team Orange");
        orangeTeam.put("material", "ORANGE_WOOL");

        Map<String, Object> orangeTeamSpawn = new LinkedHashMap<>();
        orangeTeamSpawn.put("x", 30.5);
        orangeTeamSpawn.put("y", 65.5);
        orangeTeamSpawn.put("z", -67.5);
        orangeTeamSpawn.put("yaw", 0.0);
        orangeTeamSpawn.put("pitch", 0.0);
        orangeTeam.put("spawn", orangeTeamSpawn);

        Map<String, Object> orangeShopSpawn = new LinkedHashMap<>();
        orangeShopSpawn.put("x", 34.5);
        orangeShopSpawn.put("y", 65.0);
        orangeShopSpawn.put("z", -71.5);
        orangeShopSpawn.put("yaw", 90.0);
        orangeShopSpawn.put("pitch", 0.0);
        orangeTeam.put("shop", orangeShopSpawn);

        fileConfiguration.addDefault("orange", orangeTeam);

        Map<String, Object> greenTeam = new LinkedHashMap<>();
        greenTeam.put("color", ChatColor.GREEN.toString());
        greenTeam.put("name", "Green");
        greenTeam.put("team_name", "Team Green");
        greenTeam.put("material", "GREEN_WOOL");

        Map<String, Object> greenTeamSpawn = new LinkedHashMap<>();
        greenTeamSpawn.put("x", -66.5);
        greenTeamSpawn.put("y", 65.5);
        greenTeamSpawn.put("z", -30.5);
        greenTeamSpawn.put("yaw", -90.0);
        greenTeamSpawn.put("pitch", 0.0);
        greenTeam.put("spawn", greenTeamSpawn);

        Map<String, Object> greenShopSpawn = new LinkedHashMap<>();
        greenShopSpawn.put("x", -70.5);
        greenShopSpawn.put("y", 65.0);
        greenShopSpawn.put("z", -34.5);
        greenShopSpawn.put("yaw", 0.0);
        greenShopSpawn.put("pitch", 0.0);
        greenTeam.put("shop", greenShopSpawn);

        fileConfiguration.addDefault("green", greenTeam);

        Map<String, Object> blueTeam = new LinkedHashMap<>();
        blueTeam.put("color", ChatColor.BLUE.toString());
        blueTeam.put("name", "Blue");
        blueTeam.put("team_name", "Team Blue");
        blueTeam.put("material", "BLUE_WOOL");

        Map<String, Object> blueTeamSpawn = new LinkedHashMap<>();
        blueTeamSpawn.put("x", 67.5);
        blueTeamSpawn.put("y", 65.5);
        blueTeamSpawn.put("z", -30.5);
        blueTeamSpawn.put("yaw", 90.0);
        blueTeamSpawn.put("pitch", 0.0);
        blueTeam.put("spawn", blueTeamSpawn);

        Map<String, Object> blueShopSpawn = new LinkedHashMap<>();
        blueShopSpawn.put("x", 71.5);
        blueShopSpawn.put("y", 65.0);
        blueShopSpawn.put("z", -26.5);
        blueShopSpawn.put("yaw", 180.0);
        blueShopSpawn.put("pitch", 0.0);
        blueTeam.put("shop", blueShopSpawn);

        fileConfiguration.addDefault("blue", blueTeam);

        Map<String, Object> purpleTeam = new LinkedHashMap<>();
        purpleTeam.put("color", ChatColor.DARK_PURPLE.toString());
        purpleTeam.put("name", "Purple");
        purpleTeam.put("team_name", "Team Purple");
        purpleTeam.put("material", "PURPLE_WOOL");

        Map<String, Object> purpleTeamSpawn = new LinkedHashMap<>();
        purpleTeamSpawn.put("x", 67.5);
        purpleTeamSpawn.put("y", 65.5);
        purpleTeamSpawn.put("z", 29.5);
        purpleTeamSpawn.put("yaw", 90.0);
        purpleTeamSpawn.put("pitch", 0.0);
        purpleTeam.put("spawn", purpleTeamSpawn);

        Map<String, Object> purpleShopSpawn = new LinkedHashMap<>();
        purpleShopSpawn.put("x", 71.5);
        purpleShopSpawn.put("y", 65.0);
        purpleShopSpawn.put("z", 33.5);
        purpleShopSpawn.put("yaw", 180.0);
        purpleShopSpawn.put("pitch", 0.0);
        purpleTeam.put("shop", purpleShopSpawn);

        fileConfiguration.addDefault("purple", purpleTeam);

        Map<String, Object> pinkTeam = new LinkedHashMap<>();
        pinkTeam.put("color", ChatColor.LIGHT_PURPLE.toString());
        pinkTeam.put("name", "Pink");
        pinkTeam.put("team_name", "Team Pink");
        pinkTeam.put("material", "PINK_WOOL");

        Map<String, Object> pinkTeamSpawn = new LinkedHashMap<>();
        pinkTeamSpawn.put("x", -66.5);
        pinkTeamSpawn.put("y", 65.5);
        pinkTeamSpawn.put("z", 29.5);
        pinkTeamSpawn.put("yaw", -90.0);
        pinkTeamSpawn.put("pitch", 0.0);
        pinkTeam.put("spawn", pinkTeamSpawn);

        Map<String, Object> pinkShopSpawn = new LinkedHashMap<>();
        pinkShopSpawn.put("x", -70.5);
        pinkShopSpawn.put("y", 65.0);
        pinkShopSpawn.put("z", 25.5);
        pinkShopSpawn.put("yaw", 0.0);
        pinkShopSpawn.put("pitch", 0.0);
        pinkTeam.put("shop", pinkShopSpawn);

        fileConfiguration.addDefault("pink", pinkTeam);
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

    public Set<String> getTeams() {
        return fileConfiguration.getKeys(false);
    }
}
