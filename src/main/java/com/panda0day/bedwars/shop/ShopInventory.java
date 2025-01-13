package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ShopInventory {
    public static void openInventory(Player player) {
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team == null) return;

        Inventory inventory = Bukkit.createInventory(null, 9 * 2, "Shop");
        inventory.addItem(
                new ItemManager(team.getMaterial())
                        .setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Blocks")
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.IRON_SWORD)
                        .addEnchantment(Enchantment.UNBREAKING, 5)
                        .setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Tools")
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.TNT)
                        .setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Misc")
                        .setLore(List.of(
                                ChatColor.RED + "Coming soon"
                        ))
                        .create()
        );

        player.openInventory(inventory);
    }

    public static void openBlocksInventory(Player player) {
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team == null) return;

        Inventory inventory = Bukkit.createInventory(null, 9 * 2, "Blocks");
        inventory.addItem(
                new ItemManager(team.getMaterial())
                        .setDisplayName(ChatColor.GOLD  + "8 Blocks")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 8 Blocks for",
                                ChatColor.GOLD + "2 Bricks"
                        ))
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.TERRACOTTA)
                        .setDisplayName(ChatColor.GOLD  + "2 Terracotta")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 2 Terracotta blocks for",
                                ChatColor.GOLD + "5 Bricks"
                        ))
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.END_STONE)
                        .setDisplayName(ChatColor.GOLD  + "1 Endstone")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 1 Endstone for",
                                ChatColor.GOLD + "8 Bricks"
                        ))
                        .create()
        );

        player.openInventory(inventory);
    }

    public static void openToolsInventory(Player player) {
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team == null) return;

        Inventory inventory = Bukkit.createInventory(null, 9 * 2, "Tools");
        inventory.addItem(
                new ItemManager(Material.WOODEN_SWORD)
                        .addEnchantments(List.of(
                                Enchantment.SHARPNESS,
                                Enchantment.UNBREAKING
                        ))
                        .setDisplayName(ChatColor.GOLD  + "Wooden Sword")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 1 Wooden Sword for",
                                ChatColor.GOLD + "5 Bricks"
                        ))
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.IRON_SWORD)
                        .addEnchantments(List.of(
                                Enchantment.SHARPNESS,
                                Enchantment.UNBREAKING
                        ))
                        .setDisplayName(ChatColor.GOLD  + "Iron Sword")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 1 Iron Sword for",
                                ChatColor.GOLD + "3 Iron Ingot"
                        ))
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.WOODEN_PICKAXE)
                        .addEnchantment(Enchantment.UNBREAKING, 3)
                        .setDisplayName(ChatColor.GOLD  + "Wooden Pickaxe")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 1 Wooden Pickaxe for",
                                ChatColor.GOLD + "8 Bricks"
                        ))
                        .create()
        );
        inventory.addItem(
                new ItemManager(Material.STONE_PICKAXE)
                        .addEnchantment(Enchantment.UNBREAKING, 3)
                        .addEnchantment(Enchantment.EFFICIENCY, 1)
                        .setDisplayName(ChatColor.GOLD  + "Stone Pickaxe")
                        .setLore(List.of(
                                ChatColor.GREEN + "Buy 1 Stone Pickaxe for",
                                ChatColor.GOLD + "2 Iron Ingot"
                        ))
                        .create()
        );

        player.openInventory(inventory);
    }
}
