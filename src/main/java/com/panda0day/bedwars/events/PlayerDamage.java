package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        World world = event.getEntity().getWorld();
        if (!world.getName().equals(Main.getGameConfig().getWorldName()))
            event.setCancelled(true);
    }
}
