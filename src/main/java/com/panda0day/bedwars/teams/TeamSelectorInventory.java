package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectorInventory {
    public static void openTeamSelectorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Team Selector");

        for (Team team : Main.getTeamManager().getTeams()) {
            List<String> lore = new ArrayList<>();
            team.getPlayers().forEach(teamPlayer -> {
                lore.add("» " +ChatColor.GOLD + teamPlayer.getName());
            });

            inventory.addItem(
                    new ItemManager(team.getMaterial())
                            .setDisplayName(team.getColor() + "Team " + team.getName())
                            .setLore(lore)
                            .create()
            );

        }

        player.openInventory(inventory);
    }
}
