package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.location.Locations;
import com.panda0day.bedwars.utils.ItemManager;
import com.panda0day.bedwars.location.LocationManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Main.getGameStateManager().getCurrentGameState() != GameState.LOBBY) return;

        if (Main.getGameStateManager().getPlayers().size() >= Main.getGameStateManager().getMaximumPlayers()) {
            player.kickPlayer("You have been kicked. Game is full");
            return;
        }

        if (Main.getGameStateManager().getCurrentGameState() != GameState.LOBBY) {
            player.kickPlayer("You have been kicked. Game has started.");
            return;
        }

        event.setJoinMessage(Main.getMainConfig().getPrefix() + ChatColor.GREEN + "[+] " + player.getDisplayName() + " joined the bedwars!");
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

        if (Main.getLocationManager().doesLocationExist("spawn")) {
            Locations location = Main.getLocationManager().getLocation("spawn");
            if (location != null) {
                player.teleport(location.getLocation());
            }
        }

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(Main.getGameStateManager().getCurrentMap().getCountdown());
        player.setGameMode(GameMode.ADVENTURE);

        createDefaultInventoryItems(player);

        Main.getGameStateManager().addPlayer(player);
        Main.getTeamManager().assignPlayerToTeam(player);
        Main.getGameStateManager().checkForGameStart();
    }

    private void createDefaultInventoryItems(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

        playerInventory.setItem(4, new ItemManager(Material.COMPASS)
                .setDisplayName(ChatColor.GOLD + "Team Selector")
                .setLore(List.of(
                        ChatColor.GREEN + "Choose your Team"
                ))
                .create()
        );
    }
}
