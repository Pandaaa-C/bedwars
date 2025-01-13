package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.InventoryManager;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
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

        switch (clickedItemMeta.getDisplayName()) {
            case "§6§lBlocks":
                ShopInventory.openBlocksInventory(player);
                break;
            case "§a§lTools":
                ShopInventory.openToolsInventory(player);
                break;
            default: break;
        }
    }

    public static void onBlocksInventoryInteract(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Blocks")) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        if (clickedItemMeta == null) return;

        if (clickedItemMeta.getDisplayName().equals("§68 Blocks")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 2)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 2)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(playerTeam.getMaterial())
                                    .setAmount(8)
                                    .create()
                    );
                }
            }
        } else if (clickedItemMeta.getDisplayName().equals("§62 Terracotta")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 5)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 5)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.TERRACOTTA)
                                    .setAmount(2)
                                    .create()
                    );
                }
            }
        } else if (clickedItemMeta.getDisplayName().equals("§61 Endstone")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 8)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 8)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.END_STONE)
                                    .setAmount(1)
                                    .create()
                    );
                }
            }
        }
    }

    public static void onToolsInventoryInteract(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Tools")) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        if (clickedItemMeta == null) return;

        if (clickedItemMeta.getDisplayName().equals("§6Wooden Sword")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 5)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 5)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.WOODEN_SWORD)
                                    .addEnchantments(List.of(
                                            Enchantment.SHARPNESS,
                                            Enchantment.UNBREAKING
                                    ))
                                    .setAmount(1)
                                    .create()
                    );
                }
            }
        } else if (clickedItemMeta.getDisplayName().equals("§6Iron Sword")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.IRON_INGOT, 3)) {
                if (InventoryManager.removeItemCount(player, Material.IRON_INGOT, 3)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.IRON_SWORD)
                                    .addEnchantments(List.of(
                                            Enchantment.SHARPNESS,
                                            Enchantment.UNBREAKING
                                    ))
                                    .setAmount(1)
                                    .create()
                    );
                }
            }
        } else if (clickedItemMeta.getDisplayName().equals("§6Wooden Pickaxe")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 8)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 8)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.WOODEN_PICKAXE)
                                    .addEnchantment(Enchantment.UNBREAKING, 3)
                                    .setAmount(1)
                                    .create()
                    );
                }
            }
        } else if (clickedItemMeta.getDisplayName().equals("§6Stone Pickaxe")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.IRON_INGOT, 2)) {
                if (InventoryManager.removeItemCount(player, Material.IRON_INGOT, 2)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(Material.STONE_PICKAXE)
                                    .addEnchantment(Enchantment.UNBREAKING, 3)
                                    .addEnchantment(Enchantment.EFFICIENCY, 1)
                                    .setAmount(1)
                                    .create()
                    );
                }
            }
        }
    }
}
