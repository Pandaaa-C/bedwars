package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "[+] " + player.getDisplayName() + " has left the game!");
        Team team = Main.getTeamManager().getTeamFromPlayer(player);
        if (team != null)
            team.removePlayer(player);

        // TODO: Check if GAME state and then elimate player from team etc.
        Main.getGameStateManager().removePlayer(player);

        if (Main.getGameStateManager().getCurrentGameState() == GameState.GAME) {
            Main.getGameStateManager().checkForGameEnd();
        }
    }
}
