package com.panda0day.bedwars.commands;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    public AdminCommand() {
        Main.getInstance().getLogger().info("Registered Command: AdminCommand");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("This command can only be executed by a player.");
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage("You do not have permission to start the game.");
            return false;
        }

        Location location = new Location(Bukkit.getWorld(Main.getGameConfig().getWorldName()), 0, 150, 0, 0,0);
        player.teleport(location);
        return true;
    }
}
