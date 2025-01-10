package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
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
        if (!world.getName().equals(Main.getGameConfig().getWorldName())) {
            event.setCancelled(true);

            Inventory inventory = event.getClickedInventory();
        }

        ShopInventoryInteract.onShopInventoryInteract(event);
        ShopInventoryInteract.onBlocksInventoryInteract(event);
    }
}
