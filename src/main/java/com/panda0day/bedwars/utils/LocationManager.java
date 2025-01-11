package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.util.Objects;

public class LocationManager {
    public static Location getLocation(String name) {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM locations WHERE name = ?;", name);
            while (resultSet.next()) {
                String worldName = resultSet.getString("world");
                if (!Main.getWorldManager().isWorldLoaded(worldName)) {
                    Main.getWorldManager().loadWorld(worldName);
                }
                World world = Bukkit.getWorld(worldName);
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float yaw = resultSet.getFloat("yaw");
                float pitch = resultSet.getFloat("pitch");
                return new Location(world, x, y, z, yaw, pitch);
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }

        return null;
    }

    public static boolean doesLocationExist(String name) {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM locations WHERE name = ?;", name);
            return resultSet.next();
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }

        return false;
    }

    public static boolean deleteLocation(String name) {
        ResultSet resultSet = Main.getDatabase().executeQuery("DELETE FROM locations WHERE name = ?;", name);
        return resultSet == null;
    }

    public static boolean setLocation(String name, Location location) {
        ResultSet resultSet = Main.getDatabase().executeQuery("INSERT INTO locations (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?);",
                name,
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );

        return resultSet == null;
    }

    public static boolean areLocationsEqual(Location location1, Location location2) {
        if (location1 == null || location2 == null) {
            return false;
        }

        System.out.println(location1.getWorld());
        System.out.println(location2.getWorld());

        if (location1.getWorld() == null || location2.getWorld() == null) {
            return false;
        }

        return location1.getWorld().getName().equals(location2.getWorld().getName()) &&
                location1.getBlockX() == location2.getBlockX() &&
                location1.getBlockY() == location2.getBlockY() &&
                location1.getBlockZ() == location2.getBlockZ();
    }

    public static void createDefaultTables() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS locations (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL UNIQUE,
                     world VARCHAR(255) NOT NULL,
                     x DOUBLE NOT NULL,
                     y DOUBLE NOT NULL,
                     z DOUBLE NOT NULL,
                     yaw FLOAT NOT NULL,
                     pitch FLOAT NOT NULL
                 )
                """);
    }
}
