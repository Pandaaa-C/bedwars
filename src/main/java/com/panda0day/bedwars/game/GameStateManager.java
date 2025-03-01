package com.panda0day.bedwars.game;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.map.Maps;
import com.panda0day.bedwars.spawnables.ResourceSpawner;
import com.panda0day.bedwars.spawnables.Spawnable;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.EntitySpawner;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameStateManager {
    private final Maps currentMap;
    private GameState currentGameState = GameState.LOBBY;
    private final Set<Player> players = new HashSet<>();
    private final int minimumPlayers;
    private final int maximumPlayers;
    private int countdown;
    private boolean isCountdownRunning;

    public GameStateManager() {
        this.currentMap = Main.getMapManager().getRandomMap();
        minimumPlayers = this.currentMap.getMinimumPlayers();
        maximumPlayers = this.currentMap.getMaximumPlayers();
        countdown = this.currentMap.getCountdown();
        this.isCountdownRunning = false;
    }

    public void checkForGameStart() {
        if (players.size() >= getMinimumPlayers()) {
            startCountdown();
        }
    }

    public void checkForGameEnd() {
        List<Team> aliveTeams = new ArrayList<>();
        List<Player> alivePlayers = new ArrayList<>();

        for (Player player : players) {
            Team team = Main.getTeamManager().getTeamFromPlayer(player);
            if (team != null && !team.isEliminated() && !aliveTeams.contains(team)) {
                aliveTeams.add(team);
            }

            if (player.getGameMode() != GameMode.SPECTATOR) {
                alivePlayers.add(player);
            }
        }

        if (aliveTeams.size() == 1) {
            Team winningTeam = aliveTeams.get(0);
            Bukkit.broadcastMessage(Main.getMainConfig().getPrefix() + "The game has ended! " + winningTeam.getColor() + winningTeam.getTeamName() + ChatColor.WHITE + " wins!");
            setCurrentGameState(GameState.END);
            endGame();
            return;
        }

        if (alivePlayers.size() == 1) {
            Player lastPlayer = alivePlayers.get(0);
            Team winningTeam = Main.getTeamManager().getTeamFromPlayer(lastPlayer);
            if (winningTeam == null) return;
            setCurrentGameState(GameState.END);

            Bukkit.broadcastMessage(Main.getMainConfig().getPrefix() + "The game has ended! " + winningTeam.getColor() + winningTeam.getTeamName() + ChatColor.WHITE + " wins!");
            endGame();
            return;
        }

        if (aliveTeams.isEmpty() || alivePlayers.isEmpty()) {
            setCurrentGameState(GameState.END);
            Bukkit.broadcastMessage(Main.getMainConfig().getPrefix() + "The game has ended in a draw!");
            endGame();
        }
    }

    public void endGame() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.kickPlayer("Game Over!");
                    Bukkit.getServer().spigot().restart();
                });
            }
        }.runTaskLater(Main.getInstance(), 20L * 5);
    }

    public void startCountdown() {
        if (this.isCountdownRunning) return;
        this.isCountdownRunning = true;

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
        Main.getWorldManager().loadWorld(this.currentMap.getMapWorld());

        Bukkit.getOnlinePlayers().forEach(player -> {
            Team team = Main.getTeamManager().getTeamFromPlayer(player);
            if (team == null) {
                player.kickPlayer("You are not in a team!");
                return;
            }

            World world = Bukkit.getWorld(this.currentMap.getMapWorld());
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
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
        });


        ResourceSpawner resourceSpawner = new ResourceSpawner();
        for (Spawnable spawnable : resourceSpawner.getLocations()) {
            resourceSpawner.startSpawning(spawnable);
        }

        spawnShopVillagers();
        Main.getTeamManager().eliminateEmptyTeams();
    }

    private void spawnShopVillagers() {
        for (Team team : Main.getTeamManager().getAllTeams()) {
            new EntitySpawner(EntityType.VILLAGER, team.getShopLocation(), team.getName());
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

    public boolean isCountdownRunning() {
        return isCountdownRunning;
    }

    public void setCurrentGameState(GameState gameState) {
        currentGameState = gameState;
        Main.getInstance().getLogger().info("Set game state to: " + gameState);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Maps getCurrentMap() {
        return currentMap;
    }
}
