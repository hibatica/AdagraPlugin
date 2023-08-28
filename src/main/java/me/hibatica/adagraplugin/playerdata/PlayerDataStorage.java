package me.hibatica.adagraplugin.playerdata;

import me.hibatica.adagraplugin.AdagraPlugin;

import java.sql.*;
import java.util.UUID;

public class PlayerDataStorage {
    private static AdagraPlugin plugin;
    private static Connection connection;

    public static void init(AdagraPlugin plugin) {
        PlayerDataStorage.plugin = plugin;

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");

            plugin.getLogger().info("Connected to SQLite database");

        } catch (ClassNotFoundException | SQLException e) {
            plugin.getLogger().severe("Failed to connect to SQLite database.");
            e.printStackTrace();
        }

        String sql = "CREATE TABLE IF NOT EXISTS duelstats (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uuid TEXT NOT NULL," +
                "wins INTEGER NOT NULL," +
                "losses INTEGER NOT NULL)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to create 'duelstats' table.");
            throw new RuntimeException(e);
        }
    }

    public static void shutdown() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

    public static PlayerData getPlayerData(UUID playerUuid) {

        String sql = "SELECT wins, losses FROM duelstats WHERE uuid = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playerUuid.toString());

            try (ResultSet results = statement.executeQuery()) {

                if(results.next()) {
                    int wins = results.getInt("wins");
                    int losses = results.getInt("losses");

                    DuelsData duelsData = new DuelsData(playerUuid, wins, losses);

                    return new PlayerData(playerUuid, duelsData);

                } else {

                    registerNewPlayer(playerUuid);

                    return getPlayerData(playerUuid);
                }

            }
        } catch (SQLException e) {

            plugin.getLogger().severe("Could not get player data for " + playerUuid);

            e.printStackTrace();

        }

        return null;
    }

    private static void registerNewPlayer(UUID uuid) {
        String sql = "INSERT OR IGNORE INTO duelstats (uuid, wins, losses) VALUES (?, 0, 0)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid.toString());

            statement.executeUpdate();

        } catch (SQLException e) {

            plugin.getLogger().severe("Failed to add player: " + uuid);

            e.printStackTrace();

        }
    }

    protected static int incrementPlayerDuelWins(UUID uuid) {
        String sql = "UPDATE duelstats SET wins = wins + 1 WHERE uuid = ?";
        int newWins = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                newWins = getPlayerDuelWins(uuid);
            }
        } catch (SQLException e) {
            return 1234567;
        }

        return newWins;
    }

    protected static int incrementPlayerDuelLosses(UUID uuid) {
        String sql = "UPDATE duelstats SET losses = losses + 1 WHERE uuid = ?";
        int newLosses = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                newLosses = getPlayerDuelLosses(uuid);
            }
        } catch (SQLException e) {
            return 1234567;
        }

        return newLosses;
    }

    private static int getPlayerDuelWins(UUID playerUuid) throws SQLException {
        String sql = "SELECT wins FROM duelstats WHERE uuid = ?";
        int wins = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    wins = resultSet.getInt("wins");
                }
            }
        }

        return wins;
    }

    private static int getPlayerDuelLosses(UUID playerUuid) throws SQLException {
        String sql = "SELECT losses FROM duelstats WHERE uuid = ?";
        int losses = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    losses = resultSet.getInt("losses");
                }
            }
        }

        return losses;
    }
}
