package com.panda0day.bedwars;

import com.panda0day.bedwars.configs.*;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.game.GameStateManager;
import com.panda0day.bedwars.location.LocationManager;
import com.panda0day.bedwars.location.Locations;
import com.panda0day.bedwars.map.MapManager;
import com.panda0day.bedwars.shop.ShopManager;
import com.panda0day.bedwars.teams.TeamManager;
import com.panda0day.bedwars.configs.MainConfig;
import com.panda0day.bedwars.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
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

    // Managers
    private static final WorldManager worldManager = new WorldManager();
    private static MapManager mapManager;
    private static GameStateManager gameStateManager;
    private static TeamManager teamManager;
    private static LocationManager locationManager;
    private static ShopManager shopManager;

    // Tasks
    private BukkitTask boardTask;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Starting Plugin");

        _mainConfig = new MainConfig(this);
        _databaseConfig = new DatabaseConfig("mysql.yml");

        _database = new Database(
                getDatabaseConfig().getHost(),
                getDatabaseConfig().getUsername(),
                getDatabaseConfig().getPassword(),
                getDatabaseConfig().getPort(),
                getDatabaseConfig().getDatabase());
        _database.connect();

        locationManager = new LocationManager();
        locationManager.loadLocations();

        shopManager = new ShopManager();
        shopManager.loadShopCategories();
        shopManager.loadShopItems();

        mapManager = new MapManager();
        teamManager = new TeamManager();
        gameStateManager = new GameStateManager();
        gameStateManager.setCurrentGameState(GameState.LOBBY);

        Locations location = getLocationManager().getLocation("spawn");
        if (location == null || location.getLocation().getWorld() == null) return;

        worldManager.loadWorld(location.getLocation().getWorld().getName());
        worldManager.checkAndRestoreBackup(gameStateManager.getCurrentMap().getMapWorld(), gameStateManager.getCurrentMap().getMapWorld() + "_backup");
        worldManager.loadWorld(gameStateManager.getCurrentMap().getMapWorld());

        teamManager.loadTeams();

        try {
            registerEventListenerClasses();
            registerCommandClasses();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        boardTask = getServer().getScheduler().runTaskTimer(this, PlayerBoardManager.getInstance(), 0, 1);
    }

    @Override
    public void onDisable() {
        kickAllPlayers("Server is reloading...");
        getLogger().info("Stopping Plugin");


        Locations location = getLocationManager().getLocation("spawn");
        if (location == null || location.getLocation().getWorld() == null) return;
        worldManager.unloadWorld(location.getLocation().getWorld().getName());

        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().forEach(Entity::remove);
        });
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

    public static DatabaseConfig getDatabaseConfig() {
        return _databaseConfig;
    }

    public static Database getDatabase() {
        return _database;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static ShopManager getShopManager() {
        return shopManager;
    }

    public BukkitTask getBoardTask() {
        return boardTask;
    }
}