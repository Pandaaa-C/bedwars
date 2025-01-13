package com.panda0day.bedwars.map;

public class Maps {
    private final String mapName;
    private final String mapWorld;
    private final int maximumPlayers;
    private final int minimumPlayers;
    private final int maxPlayersPerTeam;
    private final int countdown;

    public Maps(String mapName, String mapWorld, int maximumPlayers, int minimumPlayers, int maxPlayersPerTeam, int countdown) {
        this.mapName = mapName;
        this.mapWorld = mapWorld;
        this.maximumPlayers = maximumPlayers;
        this.minimumPlayers = minimumPlayers;
        this.maxPlayersPerTeam = maxPlayersPerTeam;
        this.countdown = countdown;
    }

    public String getMapName() {
        return mapName;
    }

    public String getMapWorld() {
        return mapWorld;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public int getMaxPlayersPerTeam() {
        return maxPlayersPerTeam;
    }

    public int getCountdown() {
        return countdown;
    }
}
