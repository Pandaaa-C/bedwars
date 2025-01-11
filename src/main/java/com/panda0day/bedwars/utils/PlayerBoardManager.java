package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerBoardManager implements Runnable {
    private static final PlayerBoardManager instance = new PlayerBoardManager();

    private PlayerBoardManager() {}

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard() != null && player.getScoreboard().getObjective("BEDWARS") != null) {
                updateScoreboard(player);
            } else {
                createNewScoreboard(player);
            }
        }
    }

    private void createNewScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("BEDWARS", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW +"" + ChatColor.BOLD + " « BedWars » ");

        objective.getScore(ChatColor.RESET + " ").setScore(9);
        objective.getScore(ChatColor.GOLD + " Map:").setScore(8);
        objective.getScore(ChatColor.RED + " ").setScore(6);
        objective.getScore(ChatColor.GOLD + " Team:").setScore(5);
        objective.getScore(ChatColor.GREEN + " ").setScore(3);
        objective.getScore(ChatColor.GOLD + " Players:").setScore(2);
        objective.getScore(ChatColor.BLUE + " ").setScore(0);

        Team mapTeam = scoreboard.registerNewTeam("Map");
        String mapTeamKey = ChatColor.GREEN.toString();
        mapTeam.addEntry(mapTeamKey);
        mapTeam.setPrefix("» ");
        mapTeam.setSuffix(Main.getGameStateManager().getCurrentMap().getMapName());

        objective.getScore(mapTeamKey).setScore(7);

        com.panda0day.bedwars.teams.Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);

        Team teamTeam = scoreboard.registerNewTeam("Team");
        String teamTeamKey = playerTeam.getColor().toString();
        teamTeam.addEntry(teamTeamKey);
        teamTeam.setPrefix("» ");
        teamTeam.setSuffix(playerTeam.getName());

        objective.getScore(teamTeamKey).setScore(4);

        Team playersTeam = scoreboard.registerNewTeam("Players");
        String playersTeamKey = ChatColor.YELLOW.toString();
        playersTeam.addEntry(playersTeamKey);
        playersTeam.setPrefix("» ");
        playersTeam.setSuffix(Main.getGameStateManager().getPlayers().size() + "/" + Main.getGameStateManager().getMaximumPlayers());

        objective.getScore(playersTeamKey).setScore(1);

        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        com.panda0day.bedwars.teams.Team playerTeam = Main.getTeamManager().getTeamFromPlayer(player);

        Team mapTeam = scoreboard.getTeam("Map");
        mapTeam.setSuffix(ChatColor.WHITE + Main.getGameStateManager().getCurrentMap().getMapName());

        Team teamTeam = scoreboard.getTeam("Team");
        teamTeam.setSuffix(playerTeam.getColor() + playerTeam.getName());

        Team playersTeam = scoreboard.getTeam("Players");
        playersTeam.setSuffix(ChatColor.WHITE + "" + Main.getGameStateManager().getPlayers().size() + "/" + Main.getGameStateManager().getMaximumPlayers());
    }

    public static PlayerBoardManager getInstance() {
        return instance;
    }
}
