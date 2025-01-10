package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.teams.TeamManager;
import com.panda0day.bedwars.teams.TeamSelector;
import com.panda0day.bedwars.utils.InventoryManager;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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

        if (clickedItemMeta.getDisplayName().equals("§616 Blocks")) {
            PlayerInventory playerInventory = player.getInventory();
            if (InventoryManager.hasItemCount(playerInventory, Material.BRICK, 2)) {
                if (InventoryManager.removeItemCount(player, Material.BRICK, 2)) {
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    if (playerTeam == null) return;

                    playerInventory.addItem(
                            new ItemManager(TeamSelector.getTeamWool(playerTeam.getName()))
                                    .setDisplayName(playerTeam.getColor() + "Team " + playerTeam.getName())
                                    .setAmount(16)
                                    .create()
                    );
                }
            }
        }
    }
}
