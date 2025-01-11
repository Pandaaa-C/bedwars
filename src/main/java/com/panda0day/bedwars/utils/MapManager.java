package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.map.Maps;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private final List<Maps> maps;

    public MapManager() {
        this.maps = this.getMaps();
    }

    public List<Maps> getMaps() {
        List<Maps> _maps = new ArrayList<>();
        ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM maps;");

        try {
            while (resultSet.next()) {
                String mapName = resultSet.getString("map_name");
                String mapWorld = resultSet.getString("map_world");
                int maximumPlayers = resultSet.getInt("maximum_players");
                int minimumPlayers = resultSet.getInt("minimum_players");
                int playersPerTeam = resultSet.getInt("players_per_team");
                int countdown = resultSet.getInt("countdown");

                _maps.add(new Maps(mapName, mapWorld, maximumPlayers, minimumPlayers, playersPerTeam, countdown));
                Main.getInstance().getLogger().info("[MapManager] Loaded map " + mapName);
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }


        return _maps;
    }

    public Maps getMap(String mapName) {
        return this.maps.stream().findFirst().filter(s -> s.getMapName().equals(mapName)).orElse(null);
    }

    public Maps getRandomMap() {
        return this.maps.stream().findFirst().orElse(null);
    }

    public static void createDefaultTables() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS maps (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     map_name VARCHAR(255) NOT NULL UNIQUE,
                     map_world VARCHAR(255) NOT NULL,
                     maximum_players INT(32) NOT NULL,
                     minimum_players INT(32) NOT NULL,
                     players_per_team INT(32) NOT NULL,
                     countdown INT(32) NOT NULL
                 )
                """);
    }
}
