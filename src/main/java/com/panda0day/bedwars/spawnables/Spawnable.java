package com.panda0day.bedwars.spawnables;

import org.bukkit.Location;
import org.bukkit.Material;

public class Spawnable {
    private final int tick;
    private final Location location;
    private final Material material;

    public Spawnable(int tick, Material material, Location location) {
        this.tick = tick;
        this.location = location;
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return material;
    }

    public int getTick() {
        return tick;
    }
}
