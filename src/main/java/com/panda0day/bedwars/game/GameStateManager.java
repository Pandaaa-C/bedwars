package com.panda0day.bedwars.game;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.EntitySpawner;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class GameStateManager {
    private GameState currentGameState = GameState.LOBBY;
    private final Set<Player> players = new HashSet<>();
    private final int minimumPlayers;
    private final int maximumPlayers;
    private int countdown;

    public GameStateManager() {
        minimumPlayers = Main.getGameConfig().getMinimumPlayers();
        maximumPlayers = Main.getGameConfig().getMaximumPlayers();
        countdown = Main.getGameConfig().getCountdownTime();
    }

    public void checkForGameStart() {
        if (players.size() >= minimumPlayers) {
            startCountdown();
        }
    }

    public void startCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (countdown > 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.setLevel(countdown);
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                    });
                    countdown--;
                } else {
                    startGame();
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    private void startGame() {
        setCurrentGameState(GameState.GAME);
        Main.getWorldManager().loadWorld(Main.getGameConfig().getFileConfiguration().getString("map_world"));

        Bukkit.getOnlinePlayers().forEach(player -> {
            Team team = Main.getTeamManager().getTeamFromPlayer(player);
            if (team == null) {
                player.kickPlayer("You are not in a team!");
                return;
            }

            World world = Bukkit.getWorld(Main.getGameConfig().getFileConfiguration().getString("map_world"));
            Location location = new Location(
                    world,
                    team.getSpawnLocation().getX(),
                    team.getSpawnLocation().getY(),
                    team.getSpawnLocation().getZ(),
                    team.getSpawnLocation().getYaw(),
                    team.getSpawnLocation().getPitch()
            );

            player.getInventory().clear();
            player.setLevel(0);
            player.teleport(location);
            player.setRespawnLocation(location);
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
        });

        GameResourceSpawner resourceSpawner = new GameResourceSpawner(Main.getInstance());
        resourceSpawner.startSpawning(Material.BRICK, 20L * 2);
        resourceSpawner.startSpawning(Material.IRON_INGOT, 20L * 6);
        resourceSpawner.startSpawning(Material.GOLD_INGOT, 20L * 20);

        spawnShopVillagers();
    }

    private void spawnShopVillagers() {
        for (Team team : Main.getTeamManager().getTeams()) {
            ConfigurationSection section = Main.getTeamConfig().getFileConfiguration().getConfigurationSection(team.getIdentifier() + ".shop");
            Location shopLocation = new Location(
                    Bukkit.getWorld(Main.getGameConfig().getWorldName()),
                    section.getDouble("x"),
                    section.getDouble("y"),
                    section.getDouble("z"),
                    (float) section.getDouble("yaw"),
                    (float) section.getDouble("pitch")
            );

            new EntitySpawner(EntityType.VILLAGER, shopLocation, team.getName());
        }
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void setCurrentGameState(GameState gameState) {
        currentGameState = gameState;
        Main.getInstance().getLogger().info("Set game state to: " + gameState);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
