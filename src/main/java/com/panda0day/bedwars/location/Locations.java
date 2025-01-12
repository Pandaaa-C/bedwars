package com.panda0day.bedwars.location;

import org.bukkit.Location;

public class Locations {
    public String name;
    public Location location;

    public Locations(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
