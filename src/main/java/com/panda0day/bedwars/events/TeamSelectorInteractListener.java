package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.TeamSelectorInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamSelectorInteractListener implements Listener {

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        World world = event.getPlayer().getWorld();
        switch (Main.getGameStateManager().getCurrentGameState()) {
            case LOBBY:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = event.getItem();
                    if (item == null || item.getType() == Material.AIR) {
                        return;
                    }

                    if (item != null || item.getType() != null) {
                        if (item.getType() == Material.COMPASS) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null && meta.hasDisplayName()) {
                                String displayName = ChatColor.stripColor(meta.getDisplayName());
                                if ("Team Selector".equalsIgnoreCase(displayName)) {
                                    Player player = event.getPlayer();
                                    TeamSelectorInventory.openTeamSelectorInventory(player);
                                }
                            }
                        }
                    }
                }
                break;

            default: break;
        }
        

    }
}
