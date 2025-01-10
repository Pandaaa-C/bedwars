package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamManager {
    private final List<Team> teams;

    public TeamManager() {
        this.teams = this.getTeams();
    }

    public List<Team> getTeams() {
        ResultSet  resultSet = Main.getDatabase().executeQuery("SELECT * FROM teams;");
        List<Team> teams = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String teamName = resultSet.getString("team_name");
                String color = resultSet.getString("color");
                Location spawnLocation = new Location(
                        Bukkit.getWorld("world"), // TODO: add different map names
                        resultSet.getDouble("spawnX"),
                        resultSet.getDouble("spawnY"),
                        resultSet.getDouble("spawnZ"),
                        resultSet.getFloat("spawnYaw"),
                        resultSet.getFloat("spawnPitch")
                );

                Location shopLocation = new Location(
                        Bukkit.getWorld("world"),
                        resultSet.getDouble("shopX"),
                        resultSet.getDouble("shopY"),
                        resultSet.getDouble("shopZ"),
                        resultSet.getFloat("shopYaw"),
                        resultSet.getFloat("shopPitch")
                );

                teams.add(new Team(
                        name,
                        name,
                        teamName,
                        ChatColor.getByChar(color),
                        spawnLocation,
                        Material.getMaterial(resultSet.getString("material")),
                        shopLocation
                ));
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }

        return teams;
    }

    public Team getTeamByName(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
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

    public void assignPlayerToTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().size() < Main.getGameConfig().getMaximumPerTeam()) {
                team.addPlayer(player);
                player.sendMessage(ChatColor.GREEN + "You have been assigned to the " + team.getColor() + team.getName() + ChatColor.GREEN + " team.");
                return;
            }
        }
    }

    public void updateTeam(Team team) {
        teams.remove(team);
        teams.add(team);
    }

    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}

