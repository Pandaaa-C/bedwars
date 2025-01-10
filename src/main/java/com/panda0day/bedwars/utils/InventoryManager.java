package com.panda0day.bedwars.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {
    public static boolean hasItemCount(Inventory targetInventory, Material material, int count) {
        ItemStack[] inventory = targetInventory.getContents();
        int totalCount = 0;

        for (ItemStack item : inventory) {
            if (item == null) continue;

            if (item.getType() == material) {
                totalCount += item.getAmount();
            }

            if (totalCount >= count) {
                return true;
            }
        }

        return false;
    }

    public static boolean removeItemCount(Player player, Material material, int count) {
        ItemStack[] inventory = player.getInventory().getContents();
        int remaining = count;

        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = inventory[i];
            if (item == null || item.getType() != material) continue;

            if (item.getAmount() >= remaining) {
                item.setAmount(item.getAmount() - remaining);
                if (item.getAmount() == 0) {
                    inventory[i] = null;
                }
                player.getInventory().setContents(inventory);
                return true;
            } else {
                remaining -= item.getAmount();
                inventory[i] = null;
            }
        }

        player.getInventory().setContents(inventory);
        return false;
    }
}
