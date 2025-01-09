package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.teams.TeamSelector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopInventory {
    public static void openInventory(Player player) {
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team == null) return;

        Inventory inventory = Bukkit.createInventory(null, 9 * 2, "Shop");
        ItemStack item = new ItemStack(TeamSelector.getTeamWool(team.getIdentifier()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName("Blocks");
        item.setItemMeta(meta);

        inventory.addItem(item);

        player.openInventory(inventory);
    }
}
