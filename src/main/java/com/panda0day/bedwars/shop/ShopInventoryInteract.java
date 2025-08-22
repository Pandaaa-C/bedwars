package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.InventoryManager;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopInventoryInteract {
    public static void onShopInventoryInteract(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Shop")) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        if (clickedItemMeta == null) return;

        int categoryId = Main.getShopManager().getShopItemCategories()
                .stream()
                .filter(x -> x.getCategoryItem().getType() == clickedItem.getType())
                .findFirst()
                .get()
                .getCategoryId();

        List<ShopItem> items = Main.getShopManager().getShopItems()
                .stream()
                .filter(x -> x.getCategoryId() == categoryId)
                .toList();
        if (items.size() <= 0) return;

        Inventory inventory = Bukkit.createInventory(player, 9 * 4, clickedItemMeta.getDisplayName());
        items.stream().map(ShopItem::getItem).forEach(inventory::addItem);

        player.openInventory(inventory);
    }

    public static void onShopItemInteract(InventoryClickEvent event) {
        ShopItemCategory category = Main.getShopManager().getShopItemCategories()
                .stream()
                .filter(x -> x.getCategoryName().equalsIgnoreCase(event.getView().getTitle()))
                .findFirst()
                .get();
        if (category == null) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        if (clickedItemMeta == null) return;

        ShopItem item = Main.getShopManager().getShopItems()
                .stream()
                .filter(x -> x.getItem().getType() == clickedItem.getType() && x.getCategoryId() == category.getCategoryId())
                .findFirst()
                .get();
        if (item == null) return;

        if (InventoryManager.hasItemCount(player.getInventory(), item.getCurrency(), item.getPrice())) {
            if (InventoryManager.removeItemCount(player,  item.getCurrency(), item.getPrice())) {
                Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                if (playerTeam == null) return;

                player.getInventory().addItem(
                        new ItemManager(item.getItem().getType())
                                .setAmount(item.getAmount())
                                .create()
                );
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 0.5f);
            }
        }
    }
}
