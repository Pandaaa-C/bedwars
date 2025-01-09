package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDrop implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        World world = event.getPlayer().getWorld();
        if (!world.getName().equals(Main.getGameConfig().getWorldName()))
            event.setCancelled(true);
    }
}
