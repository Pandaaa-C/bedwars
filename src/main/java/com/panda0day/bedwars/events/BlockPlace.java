package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.utils.BlockManager;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        World world = event.getPlayer().getWorld();
        if (!world.getName().equals(Main.getGameStateManager().getCurrentMap().getMapWorld()))
            event.setCancelled(event.getPlayer().getGameMode() == GameMode.CREATIVE);

        if (Main.getGameStateManager().getCurrentGameState() == GameState.GAME) {
            Block block = event.getBlock();
            BlockManager.addBreakableBlock(block);
        }
    }
}
