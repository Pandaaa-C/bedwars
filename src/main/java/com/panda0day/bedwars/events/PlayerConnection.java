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

public class PlayerConnection implements Listener {
    private final FileConfiguration gameConfig;

    public PlayerConnection() {
        gameConfig = Main.getInstance().getConfig();
    }

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

        World world = Main.getInstance().getServer().getWorld(Main.getLobbyConfig().getLobbyName());
        Location location = new Location(
                world,
                gameConfig.getDouble("lobby.spawnX"),
                gameConfig.getDouble("lobby.spawnY"),
                gameConfig.getDouble("lobby.spawnZ"),
                (float) gameConfig.getDouble("lobby.spawnYaw"),
                (float) gameConfig.getDouble("lobby.spawnPitch")
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
        ItemStack compass = ItemManager.createItem(Material.COMPASS, ChatColor.GOLD + "Team Selector");

        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

        playerInventory.setItem(4, compass);
    }
}
