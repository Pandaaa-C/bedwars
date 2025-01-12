package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
        if (playerTeam == null) event.setCancelled(true);

        event.setFormat("§8[" +playerTeam.getColor() +  playerTeam.getName() + "§8] " + ChatColor.GOLD + player.getName() + "§8: §f" + message);
    }
}
