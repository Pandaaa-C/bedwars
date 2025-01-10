package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.utils.ItemManager;
import com.panda0day.bedwars.utils.PlayerScoreboard;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
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

        World world = Bukkit.getWorld(Main.getLobbyConfig().getLobbyName());
        Location location = new Location(
                world,
                Main.getLobbyConfig().getFileConfiguration().getDouble("x"),
                Main.getLobbyConfig().getFileConfiguration().getDouble("y"),
                Main.getLobbyConfig().getFileConfiguration().getDouble("z"),
                (float) Main.getLobbyConfig().getFileConfiguration().getDouble("yaw"),
                (float) Main.getLobbyConfig().getFileConfiguration().getDouble("pitch")
        );

        player.teleport(location);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(Main.getInstance().getConfig().getInt("game.countdown"));
        player.setGameMode(GameMode.ADVENTURE);

        createDefaultInventoryItems(player);

        Main.getGameStateManager().addPlayer(player);
        Main.getTeamManager().assignPlayerToTeam(player);
        PlayerScoreboard.createScoreboard(player);
        PlayerScoreboard.updateScoreboards();
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
