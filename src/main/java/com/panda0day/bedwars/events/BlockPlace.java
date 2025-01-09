package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        World world = event.getPlayer().getWorld();
        if (!world.getName().equals(Main.getGameConfig().getWorldName()))
            event.setCancelled(event.getPlayer().getGameMode() == GameMode.CREATIVE);
    }
}
