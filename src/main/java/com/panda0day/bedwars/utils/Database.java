package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.game.GameResourceSpawner;
import com.panda0day.bedwars.teams.TeamManager;

import java.sql.*;

public class Database {
    private final String host;
    private final String user;
    private final String password;
    private final int port;
    private final String databaseName;
    private Connection connection;

    public Database(String host, String user, String password, int port, String databaseName) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.databaseName = databaseName;

        this.connection = null;
    }

    public void connect() {
        if (isConnected()) {
            System.out.println("Database already connected.");
            return;
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            Main.getInstance().getLogger().info("Database connection established.");

            this.createDefaultTables();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public void disconnect() {
        if (!isConnected()) {
            Main.getInstance().getLogger().info("Database not connected.");
            return;
        }

        try {
            connection.close();
            connection = null;
            Main.getInstance().getLogger().info("Database disconnected.");
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public Connection getConnection() {
        if (!isConnected()) {
            Main.getInstance().getLogger().info("Database not connected.");
            return null;
        }
        return connection;
    }

    public ResultSet executeQuery(String sql, Object... parameters) {
        if (!isConnected()) {
            Main.getInstance().getLogger().info("Database not connected.");
            return null;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            if (sql.trim().toUpperCase().startsWith("SELECT")) {
                return preparedStatement.executeQuery();
            } else {
                preparedStatement.executeUpdate();
                return null;
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }

    public void createTable(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table: " + e.getMessage());
        }
    }

    private void createDefaultTables() {
        LocationManager.createDefaultTables();
        MapManager.createDefaultTables();
        TeamManager.createDefaultTables();
        GameResourceSpawner.createDefaultTables();
    }
}
