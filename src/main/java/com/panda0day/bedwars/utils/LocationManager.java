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
                World world = Bukkit.getWorld(resultSet.getString("world"));
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
