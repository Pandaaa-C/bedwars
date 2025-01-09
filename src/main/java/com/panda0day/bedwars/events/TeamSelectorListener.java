package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.teams.TeamManager;
import com.panda0day.bedwars.teams.TeamSelector;
import com.panda0day.bedwars.utils.PlayerScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TeamSelectorListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        World world = event.getWhoClicked().getWorld();
        if (world.getName().equals(Main.getGameConfig().getWorldName())) return;

        event.setCancelled(true);

        Inventory inventory = event.getInventory();
        String title = ChatColor.stripColor(event.getView().getTitle());

        if (!"Team Selector".equals(title)) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null ||clickedItem.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        Material material = clickedItem.getType();

        String team = null;
        for (Map.Entry<String, Material> entry : TeamSelector.teamWoolColours.entrySet()) {
            if (entry.getValue() == material) {
                team = entry.getKey();
                break;
            }
        }

        if (team != null) {
            Team oldTeam = Main.getTeamManager().getTeamFromPlayer(player);
            Team newTeam = Main.getTeamManager().getTeamByName(team);
            if (oldTeam == newTeam) {
                player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "You already are in this team.");
                return;
            }

            if (oldTeam != null) {
                oldTeam.removePlayer(player);
                Main.getTeamManager().updateTeam(oldTeam);
            }

            if (newTeam.isFull()) {
                player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "The Team " + newTeam.getColor() + newTeam.getName() + ChatColor.RED + " is already full.");
                return;
            }
            newTeam.addPlayer(player);
            Main.getTeamManager().updateTeam(newTeam);
            PlayerScoreboard.updateScoreboards();
        }

        player.closeInventory();
    }
}
