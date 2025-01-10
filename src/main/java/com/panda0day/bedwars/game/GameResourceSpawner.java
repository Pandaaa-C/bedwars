package com.panda0day.bedwars.game;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GameResourceSpawner {
    private final JavaPlugin plugin;

    public GameResourceSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startSpawning(Material material, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : getLocations()) {
                    World world = location.getWorld();
                    if (world != null) {
                        world.dropItemNaturally(location, new ItemStack(material));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, ticks);
    }

    public List<Location> getLocations() {
        ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM spawnables");
        List<Location> locations = new ArrayList<>();

        try {
            while (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("world"));
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                locations.add(new Location(world, x, y, z));
            }

            return locations;
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }

        return new ArrayList<>();
    }

    public static void createDefaultTables() {

    }
}
