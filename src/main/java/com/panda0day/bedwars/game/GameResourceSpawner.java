package com.panda0day.bedwars.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class GameResourceSpawner {
    private final JavaPlugin plugin;

    public GameResourceSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startSpawning(Material material, List<Location> spawnLocations, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : spawnLocations) {
                    World world = location.getWorld();
                    if (world != null) {
                        world.dropItemNaturally(location, new ItemStack(material));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, ticks);
    }
}
