package com.panda0day.bedwars.commands;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.location.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            System.out.println(commandSender.getName() + " is not a player");
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "§cYou dont have permission to use this command!");
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "§6Usage: /setspawn");
            return false;
        }

        Main.getLocationManager().setLocation("spawn", player.getLocation());
        player.sendMessage(Main.getMainConfig().getPrefix() + "§aYou have set the spawn location ");

         return true;
    }
}
