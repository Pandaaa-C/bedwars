package com.panda0day.bedwars.events;

import com.panda0day.bedwars.shop.ShopInventory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerEntityInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager villager = (Villager) event.getRightClicked();

            ShopInventory.openInventory(player);
        }
    }
}
