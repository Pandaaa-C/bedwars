package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamManager {
    private final List<Team> teams;

    public TeamManager() {
        teams = new ArrayList<>();

        Set<String> teamNames = Main.getTeamConfig().getTeams();
        for (String team : teamNames) {
            String name = Main.getTeamConfig().getFileConfiguration().getString(team + ".name");
            String teamName = Main.getTeamConfig().getFileConfiguration().getString(team + ".team_name");
            String chatColor = Main.getTeamConfig().getFileConfiguration().getString(team + ".color");
            if (chatColor == null)
                chatColor = "§f";

            chatColor = chatColor.replace("§", "");

            ConfigurationSection section = Main.getTeamConfig().getFileConfiguration().getConfigurationSection(team + ".spawn");
            Location location = new Location(
                    Bukkit.getWorld(Main.getGameConfig().getWorldName()),
                    section.getDouble("x"),
                    section.getDouble("y"),
                    section.getDouble("z"),
                    (float) section.getDouble("yaw"),
                    (float) section.getDouble("pitch")
            );

            teams.add(new Team(team, name, teamName, ChatColor.getByChar(chatColor), location));
        }
    }

    public List<Team> getTeams() {
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

