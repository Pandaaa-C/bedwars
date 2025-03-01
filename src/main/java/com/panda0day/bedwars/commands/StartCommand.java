package com.panda0day.bedwars.commands;

import com.panda0day.bedwars.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        if (!player.hasPermission("bedwars.start")) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "You do not have permission to start the game.");
            return true;
        }

        Main.getGameStateManager().setCountdown(5);

        if (!Main.getGameStateManager().isCountdownRunning())
            Main.getGameStateManager().startCountdown();
        return true;
    }
}
