package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.teams.Team;
import com.panda0day.bedwars.teams.TeamManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerScoreboard {
    private static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static void createScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + " « BedWars » ");

        boards.put(player.getUniqueId(), board);
    }

    public static void updateScoreboard(FastBoard board) {
        Team playerTeam = Main.getTeamManager().getTeamFromPlayer(board.getPlayer());

        board.updateLines(
                "",
                ChatColor.GOLD + "Map:",
                "» " + Main.getGameStateManager().getCurrentMap().getMapName(),
                "",
                ChatColor.GOLD + "Team:",
                "» " + playerTeam.getColor() + playerTeam.getName(),
                "",
                ChatColor.GOLD + "Players:",
                "» " + Main.getGameStateManager().getPlayers().size() + "/" + Main.getGameStateManager().getMaximumPlayers(),
                ""
        );
    }

    public static Map<UUID, FastBoard> getBoards() {
        return boards;
    }

    public static void removeBoard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public static void updateScoreboards() {
        for (FastBoard board : boards.values()) {
            updateScoreboard(board);
        }
    }
}
