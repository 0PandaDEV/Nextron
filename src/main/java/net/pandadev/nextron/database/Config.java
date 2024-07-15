package net.pandadev.nextron.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private static final String DB_URL = "jdbc:sqlite:plugins/Nextron/data.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
                stmt.execute("PRAGMA journal_mode = DELETE;");
                stmt.execute("PRAGMA synchronous = FULL;");
            }
            connection.setAutoCommit(true);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, "Failed to close database connection", e);
        }
        connection = null;
    }

    public static void executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } finally {
            closeConnection();
        }
    }

    public static void executeQuery(String sql, ResultSetHandler handler) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            handler.handle(rs);
        } finally {
            closeConnection();
        }
    }

    @FunctionalInterface
    public interface ResultSetHandler {
        void handle(ResultSet rs) throws SQLException;
    }
}