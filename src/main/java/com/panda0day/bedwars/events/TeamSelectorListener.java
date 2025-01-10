package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
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
import org.bukkit.inventory.meta.ItemMeta;

public class TeamSelectorListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        World world = event.getWhoClicked().getWorld();
        if (world.getName().equals(Main.getGameStateManager().getCurrentMap().getMapWorld())) return;

        event.setCancelled(true);

        Inventory inventory = event.getInventory();
        String title = ChatColor.stripColor(event.getView().getTitle());

        if (!"Team Selector".equals(title)) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null ||clickedItem.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        Material material = clickedItem.getType();
        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (itemMeta == null) return;

        String team = itemMeta.getDisplayName().substring(2);
        Team oldTeam = Main.getTeamManager().getTeamFromPlayer(player);
        Team newTeam = Main.getTeamManager().getTeamByName(team);
        if (newTeam == null) {
            player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "Error try restarting.");
            return;
        }

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

        player.closeInventory();
    }
}
