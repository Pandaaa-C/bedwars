package com.panda0day.bedwars;

import com.panda0day.bedwars.configs.*;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.game.GameStateManager;
import com.panda0day.bedwars.teams.TeamManager;
import com.panda0day.bedwars.configs.MainConfig;
import com.panda0day.bedwars.utils.Database;
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
    private static Database _database;

    // Configs
    private static MainConfig _mainConfig;
    private static DatabaseConfig _databaseConfig;
    private static GameConfig _gameConfig;

    // Managers
    private static final WorldManager worldManager = new WorldManager();
    private static GameStateManager gameStateManager;
    private static TeamManager teamManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting Plugin");

        _mainConfig = new MainConfig(this);
        _databaseConfig = new DatabaseConfig("mysql.yml");
        _gameConfig = new GameConfig("game.yml");

        _database = new Database(
                getDatabaseConfig().getHost(),
                getDatabaseConfig().getUsername(),
                getDatabaseConfig().getPassword(),
                getDatabaseConfig().getPort(),
                getDatabaseConfig().getDatabase());
        _database.connect();

        teamManager = new TeamManager();
        gameStateManager = new GameStateManager();
        gameStateManager.setCurrentGameState(GameState.LOBBY);

        try {
            registerEventListenerClasses();
            registerCommandClasses();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        worldManager.loadWorld(Main.getMainConfig().getLobbyWorld());
        worldManager.checkAndRestoreBackup(Main.getGameConfig().getFileConfiguration().getString("map_world"), Main.getGameConfig().getFileConfiguration().getString("map_world") + "_backup");
        worldManager.loadWorld(Main.getGameConfig().getFileConfiguration().getString("map_world"));
    }

    @Override
    public void onDisable() {
        kickAllPlayers("Server is reloading...");
        getLogger().info("Stopping Plugin");

        worldManager.unloadWorld("spawn");
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

    private void registerCommandClasses() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Reflections reflections = new Reflections("com.panda0day.bedwars.commands");
        Set<Class<? extends CommandExecutor>> commandClasses = reflections.getSubTypesOf(CommandExecutor.class);
        getLogger().info("Loading " + commandClasses.size() + " commands");

        for (Class<? extends CommandExecutor> commandClass : commandClasses) {
            CommandExecutor command = commandClass.getDeclaredConstructor().newInstance();
            String commandName = command.getClass().getSimpleName().replace("Command", "");

            Objects.requireNonNull(getCommand(commandName)).setExecutor(command);

            getLogger().info("Registered command: " + commandClass.getSimpleName());
        }
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

    public static MainConfig getMainConfig() {
        return _mainConfig;
    }

    public static GameConfig getGameConfig() {
        return _gameConfig;
    }

    public static DatabaseConfig getDatabaseConfig() {
        return _databaseConfig;
    }

    public static Database getDatabase() {
        return _database;
    }
}