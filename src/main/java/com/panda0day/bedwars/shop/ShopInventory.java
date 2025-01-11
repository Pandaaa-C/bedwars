package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        player.openInventory(inventory);
    }
}
