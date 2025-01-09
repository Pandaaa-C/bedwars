package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");
        if (Main.getGameStateManager().getCurrentGameState() != GameState.GAME) return;

        Player player = event.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            player.spigot().respawn();
        }, 1L);
    }

}
