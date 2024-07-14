package net.pandadev.nextron.config;

import org.bukkit.entity.Player;

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

    public static void initializeUser(Player player) {
        String checkSql = "SELECT COUNT(*) FROM user_settings WHERE uuid = ?";
        String insertSql = "INSERT INTO user_settings (uuid, vanish_message, vanish_vanished, feedback, allowtpas, nick) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); var checkStmt = conn.prepareStatement(checkSql); var insertStmt = conn.prepareStatement(insertSql)) {

            checkStmt.setString(1, player.getUniqueId().toString());
            try (var rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    insertStmt.setString(1, player.getUniqueId().toString());
                    insertStmt.setBoolean(2, true);
                    insertStmt.setBoolean(3, false);
                    insertStmt.setBoolean(4, true);
                    insertStmt.setBoolean(5, true);
                    insertStmt.setString(6, player.getName());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, "Error initializing user settings", e);
        }
    }
}