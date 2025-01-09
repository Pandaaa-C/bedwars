package com.panda0day.bedwars.commands;

import com.panda0day.bedwars.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStartCommand implements CommandExecutor {
    public ForceStartCommand() {
        Main.getInstance().getLogger().info("Registered Command: ForceStartCommand");
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

        Main.getGameStateManager().setCountdown(3);
        Main.getGameStateManager().startCountdown();
        return true;
    }
}
