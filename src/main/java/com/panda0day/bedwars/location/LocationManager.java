package com.panda0day.bedwars.location;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationManager {
    private final List<Locations> locationsList;

    public LocationManager() {
        this.locationsList = new ArrayList<>();
    }

    public void loadLocations() {
        try {
            ResultSet rs = Main.getDatabase().executeQuery("SELECT * FROM locations");
            while (rs.next()) {
                String name = rs.getString("name");
                String worldName = rs.getString("world");
                if (!Main.getWorldManager().isWorldLoaded(worldName)) {
                    Main.getWorldManager().loadWorld(worldName);
                }
                World world = Bukkit.getWorld(worldName);
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                this.locationsList.add(new Locations(name, new Location(world, x, y, z, yaw, pitch)));
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }
    }

    public Locations getLocation(String name) {
        return this.locationsList.stream().findFirst().filter(x -> x.getName().equalsIgnoreCase(name)).orElse(null);
    }

    public boolean doesLocationExist(String name) {
        return this.locationsList.stream().findFirst().filter(x -> x.getName().equalsIgnoreCase(name)).orElse(null) != null;
    }

    public boolean deleteLocation(String name) {
        Locations location = this.getLocation(name);
        if (location == null) return false;
        this.locationsList.remove(location);

        ResultSet resultSet = Main.getDatabase().executeQuery("DELETE FROM locations WHERE name = ?;", name);
        return resultSet == null;
    }

    public boolean setLocation(String name, Location location) {
        Main.getDatabase().executeQuery(
                "INSERT INTO locations (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE world = VALUES(world), x = VALUES(x), y = VALUES(y), z = VALUES(z), yaw = VALUES(yaw), pitch = VALUES(pitch);",
                name,
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );

        int idx = -1;
        for (int i = 0; i < this.locationsList.size(); i++) {
            if (this.locationsList.get(i).getName().equalsIgnoreCase(name)) {
                idx = i; break;
            }
        }
        if (idx >= 0) {
            this.locationsList.set(idx, new Locations(name, location));
        } else {
            this.locationsList.add(new Locations(name, location));
        }
        return true;
    }

    public boolean areLocationsEqual(Location location1, Location location2) {
        if (location1 == null || location2 == null) {
            return false;
        }

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
                 );
               """);
    }
}
