package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.shop.ShopInventory;
import com.panda0day.bedwars.shop.ShopInventoryInteract;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class ItemClick implements Listener {

    @EventHandler
    public void onItemClickChange(InventoryClickEvent event) {
        World world = event.getWhoClicked().getWorld();
        if (!world.getName().equals(Main.getGameStateManager().getCurrentMap().getMapWorld())) {
            event.setCancelled(true);
        }

        ShopInventoryInteract.onShopInventoryInteract(event);
        ShopInventoryInteract.onShopItemInteract(event);
    }
}
