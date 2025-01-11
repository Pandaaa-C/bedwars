package com.panda0day.bedwars.events;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameState;
import com.panda0day.bedwars.teams.Team;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockDestroy implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        World world = event.getPlayer().getWorld();
        event.setCancelled(true);

        if (world.getName().equals(Main.getGameStateManager().getCurrentMap().getMapWorld()) && Main.getGameStateManager().getCurrentGameState() == GameState.GAME) {
            event.setCancelled(false);
            Block block = event.getBlock();
            if (block.getType() == Material.RED_BED) {
                BlockData blockData = block.getBlockData();
                if (blockData instanceof Bed bed) {
                    Location location = block.getLocation();

                    if (bed.getPart() == Bed.Part.HEAD) {
                        BlockFace face = bed.getFacing();
                        location = block.getLocation().add(face.getOppositeFace().getDirection());
                    }

                    Player player = event.getPlayer();
                    Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);
                    Team bedTeam = Main.getTeamManager().getTeamByBedLocation(location);
                    if (bedTeam != null && playerTeam != null) {
                        if (playerTeam == bedTeam) {
                            event.setCancelled(true);
                            player.sendMessage(Main.getMainConfig().getPrefix() + "§cYou cannot destroy your own Bed!");
                            return;
                        }

                        if (bedTeam.isEliminated()) {
                            event.setCancelled(true);
                            player.sendMessage(Main.getMainConfig().getPrefix() + "§cTeam is already eliminated!");
                            return;
                        }

                        bedTeam.setEliminated(true);
                        event.setCancelled(true);
                        event.setDropItems(false);
                        block.breakNaturally();

                        Bukkit.getOnlinePlayers().forEach(_player -> {
                            _player.sendMessage(Main.getMainConfig().getPrefix() + bedTeam.getColor() + bedTeam.getTeamName() + " §ahas been eliminated!");
                        });
                    }
                }
            }
        }
    }
}
