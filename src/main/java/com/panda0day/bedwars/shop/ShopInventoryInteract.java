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
import java.util.Optional;

public class ShopInventoryInteract {
    public static void onShopInventoryInteract(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!event.getView().getTitle().equalsIgnoreCase("Shop")) return;
        if (event.getClickedInventory() == null || event.getClickedInventory() != event.getView().getTopInventory()) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) return;

        Optional<ShopItemCategory> optCat = Main.getShopManager().getShopItemCategories()
                .stream()
                .filter(x -> x.getCategoryItem() != null && x.getCategoryItem().getType() == clickedItem.getType())
                .findFirst();

        if (optCat.isEmpty()) return;
        ShopItemCategory category = optCat.get();

        List<ShopItem> items = Main.getShopManager().getShopItems()
                .stream()
                .filter(x -> x.getCategoryId() == category.getCategoryId())
                .toList();

        if (items.isEmpty()) return;

        Inventory inv = Bukkit.createInventory(player, 9 * 4, category.getCategoryName());
        for (ShopItem si : items) {
            if (si.getItem() != null) inv.addItem(si.getItem());
        }

        player.openInventory(inv);
    }

    public static void onShopItemInteract(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null || event.getView().getTopInventory() != event.getClickedInventory()) return;

        Optional<ShopItemCategory> optCat = Main.getShopManager().getShopItemCategories()
                .stream()
                .filter(x -> x.getCategoryName().equalsIgnoreCase(event.getView().getTitle()))
                .findFirst();
        if (optCat.isEmpty()) return;
        ShopItemCategory category = optCat.get();

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        ItemMeta clickedMeta = clickedItem.getItemMeta();
        String clickedName = clickedMeta != null && clickedMeta.hasDisplayName() ? org.bukkit.ChatColor.stripColor(clickedMeta.getDisplayName()) : null;

        List<ShopItem> candidates = Main.getShopManager().getShopItems()
                .stream()
                .filter(x -> x.getCategoryId() == category.getCategoryId())
                .filter(x -> x.getItem() != null && x.getItem().getType() == clickedItem.getType())
                .toList();
        if (candidates.isEmpty()) return;

        ShopItem item;
        if (candidates.size() == 1) {
            item = candidates.get(0);
        } else {
            item = candidates.stream().filter(x -> {
                ItemMeta m = x.getItem().getItemMeta();
                String n = (m != null && m.hasDisplayName()) ? org.bukkit.ChatColor.stripColor(m.getDisplayName()) : null;
                return n != null && n.equalsIgnoreCase(clickedName);
            }).findFirst().orElse(null);
            if (item == null) return;
        }

        Player player = (Player) event.getWhoClicked();
        if (!InventoryManager.hasItemCount(player.getInventory(), item.getCurrency(), item.getPrice())) return;
        if (!InventoryManager.removeItemCount(player, item.getCurrency(), item.getPrice())) return;

        Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
        if (playerTeam == null) return;

        player.getInventory().addItem(new ItemManager(item.getItem().getType()).setAmount(item.getAmount()).create());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 0.5f);
    }
}
