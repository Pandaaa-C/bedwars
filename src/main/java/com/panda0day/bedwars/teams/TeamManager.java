package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.map.Maps;
import com.panda0day.bedwars.location.LocationManager;
import com.panda0day.bedwars.utils.BedUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private final List<Team> teams;

    public TeamManager() {
        this.teams = new ArrayList<>();
    }

    public void loadTeams() {
        ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM teams;");
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String teamName = resultSet.getString("team_name");
                String color = resultSet.getString("color");
                Maps map = Main.getMapManager().getMap(resultSet.getString("map"));
                if (map == null) continue;

                Location spawnLocation = new Location(
                        Bukkit.getWorld(map.getMapWorld()),
                        resultSet.getDouble("spawnX"),
                        resultSet.getDouble("spawnY"),
                        resultSet.getDouble("spawnZ"),
                        resultSet.getFloat("spawnYaw"),
                        resultSet.getFloat("spawnPitch")
                );

                Location shopLocation = new Location(
                        Bukkit.getWorld(map.getMapWorld()),
                        resultSet.getDouble("shopX"),
                        resultSet.getDouble("shopY"),
                        resultSet.getDouble("shopZ"),
                        resultSet.getFloat("shopYaw"),
                        resultSet.getFloat("shopPitch")
                );

                Location bedLocation = new Location(
                        Bukkit.getWorld(map.getMapWorld()),
                        resultSet.getDouble("bedX"),
                        resultSet.getDouble("bedY"),
                        resultSet.getDouble("bedZ"),
                        resultSet.getFloat("bedYaw"),
                        resultSet.getFloat("bedPitch")
                );

                this.teams.add(new Team(
                        name,
                        name,
                        teamName,
                        ChatColor.getByChar(color.replace("§", "")),
                        spawnLocation,
                        Material.getMaterial(resultSet.getString("material")),
                        shopLocation,
                        bedLocation
                ));

                BedUtil.placeBed(bedLocation, Material.RED_BED, bedLocation.getYaw());
                Main.getInstance().getLogger().info("[TeamManager] Team " + name + " has been loaded");
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }
    }

    public Team getTeamByName(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name) || team.getTeamName().equalsIgnoreCase(name)) {
                return team;
            }
        }

        return null;
    }

    public Team getTeamFromPlayer(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public Team getTeamByMaterial(Material material) {
        for (Team team : teams) {
            if (team.getMaterial() == material) {
                return team;
            }
        }

        return null;
    }

    public Team getTeamByBedLocation(Location location) {
        for (Team team : teams) {
            if (Main.getLocationManager().areLocationsEqual(location, team.getBedLocation())) {
                return team;
            }
        }
        return null;
    }

    public void assignPlayerToTeam(Player player) {
        for (Team team : teams) {
            Main.getInstance().getLogger().info(team.getName());
            if (team.getPlayers().size() < Main.getGameStateManager().getCurrentMap().getMaxPlayersPerTeam()) {
                team.addPlayer(player);
                updateTeam(team);
                player.sendMessage(ChatColor.GREEN + "You have been assigned to the " + team.getColor() + team.getName() + ChatColor.GREEN + " team.");
                return;
            }
        }
    }

    public void updateTeam(Team updatedTeam) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getName().equals(updatedTeam.getName())) {
                teams.set(i, updatedTeam);
                return;
            }
        }
    }

    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public void eliminateEmptyTeams() {
        this.teams.forEach(team -> {
            if (team.getPlayers().size() < Main.getGameStateManager().getCurrentMap().getMaxPlayersPerTeam()) {
                team.setEliminated(true);
            }
        });
    }

    public static void createDefaultTables() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS teams (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL UNIQUE,
                     team_name VARCHAR(255) NOT NULL,
                     map VARCHAR(255) NOT NULL,
                     color VARCHAR(255) NOT NULL,
                     material VARCHAR(255) NOT NULL,
                     spawnX DOUBLE NOT NULL,
                     spawnY DOUBLE NOT NULL,
                     spawnZ DOUBLE NOT NULL,
                     spawnYaw FLOAT NOT NULL,
                     spawnPitch FLOAT NOT NULL,
                     shopX DOUBLE NOT NULL,
                     shopY DOUBLE NOT NULL,
                     shopZ DOUBLE NOT NULL,
                     shopYaw FLOAT NOT NULL,
                     shopPitch FLOAT NOT NULL,
                     bedX DOUBLE NOT NULL,
                     bedY DOUBLE NOT NULL,
                     bedZ DOUBLE NOT NULL,
                     bedYaw FLOAT NOT NULL,
                     bedPitch FLOAT NOT NULL
                 )
                """);
    }

    public List<Team> getAllTeams() {
        return this.teams;
    }
}

