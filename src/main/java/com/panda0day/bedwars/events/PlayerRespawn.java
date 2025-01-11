package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.utils.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (Main.getGameStateManager().getCurrentGameState() != GameState.GAME) return;

        Player player = event.getPlayer();
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team == null) {
            Location location = LocationManager.getLocation("spawn");
            assert location != null;
            event.setRespawnLocation(location);
            return;
        }

        if (team.isEliminated()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Main.getMainConfig().getPrefix() + "§cYour bed was destroyed!");
        }

        Main.getGameStateManager().checkForGameEnd();
        event.setRespawnLocation(team.getSpawnLocation());
    }
}
