package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldWeatherChange implements Listener {
    @EventHandler
    public void onWorldWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
