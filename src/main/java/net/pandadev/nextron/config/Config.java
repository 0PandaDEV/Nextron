package net.pandadev.nextron.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private static final String DB_URL = "jdbc:sqlite:plugins/Nextron/data.db";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL);
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, "Failed to establish database connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
        }
        return connection;
    }
}