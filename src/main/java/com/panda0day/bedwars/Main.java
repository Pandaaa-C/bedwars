package com.panda0day.bedwars;

import com.panda0day.bedwars.commands.AdminCommand;
import com.panda0day.bedwars.commands.ForceStartCommand;
import com.panda0day.bedwars.configs.GameConfig;
import com.panda0day.bedwars.configs.LobbyConfig;
import com.panda0day.bedwars.configs.SpawnablesConfig;
import com.panda0day.bedwars.configs.TeamConfig;
import com.panda0day.bedwars.game.GameResourceSpawner;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.game.GameStateManager;
import com.panda0day.bedwars.teams.TeamManager;
import com.panda0day.bedwars.utils.Config;
import com.panda0day.bedwars.utils.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;

public class Main extends JavaPlugin {
    private static Main instance;

    // Configs
    private static Config _mainConfig;
    private static GameConfig _gameConfig;
    private static TeamConfig _teamConfig;
    private static LobbyConfig _lobbyConfig;
    private static SpawnablesConfig _spawnablesConfig;

    // Managers
    private static final WorldManager worldManager = new WorldManager();
    private static GameStateManager gameStateManager;
    private static TeamManager teamManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting Plugin");

        _mainConfig = new Config(this);
        _gameConfig = new GameConfig("game.yml");
        _teamConfig = new TeamConfig("teams.yml");
        _lobbyConfig = new LobbyConfig("lobby.yml");
        _spawnablesConfig = new SpawnablesConfig("spawnables.yml");

        teamManager = new TeamManager();
        gameStateManager = new GameStateManager();
        gameStateManager.setCurrentGameState(GameState.LOBBY);

        try {
            registerEventListenerClasses();
            registerCommands();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        worldManager.loadWorld(Main.getLobbyConfig().getFileConfiguration().getString("world"));
        worldManager.checkAndRestoreBackup(Main.getGameConfig().getFileConfiguration().getString("map_world"), Main.getGameConfig().getFileConfiguration().getString("map_world") + "_backup");
        worldManager.loadWorld(Main.getGameConfig().getFileConfiguration().getString("map_world"));
    }

    @Override
    public void onDisable() {
        kickAllPlayers("Server is reloading...");
        getLogger().info("Stopping Plugin");

        worldManager.unloadWorld(Main.getLobbyConfig().getFileConfiguration().getString("world"));
    }

    private void kickAllPlayers(String reason) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(reason);
        }
    }

    private void registerEventListenerClasses() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Reflections reflections = new Reflections("com.panda0day.bedwars.events");
        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);
        getLogger().info("Loading " + listenerClasses.size() + " EventListeners");

        for (Class<? extends Listener> listenerClass : listenerClasses) {
            Listener listener = listenerClass.getDeclaredConstructor().newInstance();
            getServer().getPluginManager().registerEvents(listener, this);
            getLogger().info("Registered event: " + listener.getClass().getSimpleName());
        }
    }

    private void registerCommands() {
        getLogger().info("Loading " + getCommandClasses() + " Commands");
        Objects.requireNonNull(getCommand("forcestart")).setExecutor(new ForceStartCommand());
        Objects.requireNonNull(getCommand("admintp")).setExecutor(new AdminCommand());
    }

    private int getCommandClasses() {
        Reflections reflections = new Reflections("com.panda0day.bedwars.commands");
        Set<Class<? extends CommandExecutor>> commandClasses = reflections.getSubTypesOf(CommandExecutor.class);

        return commandClasses.size();
    }

    public static Main getInstance() {
        return instance;
    }


    public static WorldManager getWorldManager() {
        return worldManager;
    }

    public static GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static Config getMainConfig() {
        return _mainConfig;
    }

    public static GameConfig getGameConfig() {
        return _gameConfig;
    }

    public static TeamConfig getTeamConfig() {
        return _teamConfig;
    }

    public static LobbyConfig getLobbyConfig() {
        return _lobbyConfig;
    }

    public static SpawnablesConfig getSpawnablesConfig() {
        return _spawnablesConfig;
    }


}