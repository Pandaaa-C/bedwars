package com.panda0day.bedwars.spawnables;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResourceSpawner {
    public void startSpawning(Spawnable spawnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = spawnable.getLocation().getWorld();
                if (world != null) {
                    world.dropItemNaturally(spawnable.getLocation(), new ItemStack(spawnable.getMaterial()));
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, spawnable.getTick() * 20L);
    }

    public List<Spawnable> getLocations() {
        ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM spawnables;");
        List<Spawnable> spawnable = new ArrayList<>();

        try {
            while (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("world"));
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                spawnable.add(new Spawnable(
                        resultSet.getInt("tick"),
                        Material.getMaterial(resultSet.getString("material")),
                        new Location(world, x, y, z)
                ));
            }

            return spawnable;
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }

        return new ArrayList<>();
    }

    public static void createDefaultTables() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS spawnables (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     material VARCHAR(255) NOT NULL UNIQUE,
                     world VARCHAR(255) NOT NULL,
                     tick INT(32) NOT NULL,
                     x DOUBLE NOT NULL,
                     y DOUBLE NOT NULL,
                     z DOUBLE NOT NULL
                 )
                """);
    }
}
