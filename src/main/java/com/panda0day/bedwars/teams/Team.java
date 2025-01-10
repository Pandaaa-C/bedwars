package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
    private final Material material;
    private final Location shopLocation;

    public Team(String identifier, String name, String teamName, ChatColor color, Location spawnLocation, Material material, Location shopLocation) {
        this.identifier = identifier;
        this.name = name;
        this.teamName = teamName;
        this.players = new ArrayList<Player>();
        this.color = color;
        this.spawnLocation = spawnLocation;
        this.material = material;
        this.shopLocation = shopLocation;
    }

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public Material getMaterial() {
        return material;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public ChatColor getColor() {
        return color;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getShopLocation() {
        return shopLocation;
    }

    public void addPlayer(Player player) {
        if (players.size() < Main.getGameStateManager().getMaximumPlayers()) {
            players.add(player);
            player.sendMessage(Main.getMainConfig().getPrefix() + "You have joined " + getColor() + getTeamName());
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isFull() {
        return players.size() >= Main.getGameStateManager().getCurrentMap().getMaxPlayersPerTeam();
    }

    public boolean hasSpace() {
        return players.size() < Main.getGameStateManager().getCurrentMap().getMaxPlayersPerTeam();
    }
}
