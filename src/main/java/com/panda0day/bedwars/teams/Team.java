package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String identifier;
    private final String name;
    private final String teamName;
    private final List<Player> players;
    private final ChatColor color;
    private final Location spawnLocation;

    public Team(String identifier, String name, String teamName, ChatColor color, Location spawnLocation) {
        this.identifier = identifier;
        this.name = name;
        this.teamName = teamName;
        this.players = new ArrayList<Player>();
        this.color = color;
        this.spawnLocation = spawnLocation;
    }

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ChatColor getColor() {
        return color;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void addPlayer(Player player) {
        if (players.size() < Main.getGameConfig().getMaximumPerTeam()) {
            players.add(player);
            player.sendMessage(Main.getMainConfig().getPrefix() + "You have joined Team " + getColor() + getName());
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isFull() {
        return players.size() >= Main.getGameConfig().getMaximumPerTeam();
    }

    public boolean hasSpace() {
        return players.size() < Main.getGameConfig().getMaximumPerTeam();
    }
}
