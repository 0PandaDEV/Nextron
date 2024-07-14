package net.pandadev.nextron.utils;

import net.pandadev.nextron.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsAPI {

    public static void initializeUser(Player player) {
        String checkSql = "SELECT COUNT(*) FROM user_settings WHERE uuid = ?";
        String insertSql = "INSERT INTO user_settings (uuid, vanish_message, vanish_vanished, feedback, allowtpas, nick) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Config.getConnection();
             var checkStmt = conn.prepareStatement(checkSql);
             var insertStmt = conn.prepareStatement(insertSql)) {
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
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error initializing user settings", e);
        }
    }

    public static String getNick(Player player) {
        String sql = "SELECT nick FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nick");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error getting player nick", e);
        }
        return player.getName();
    }

    public static void setNick(Player player, String nick) {
        String sql = "UPDATE user_settings SET nick = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nick);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting player nick", e);
        }
    }

    public static Location getLastPosition(Player player) {
        String sql = "SELECT lastback FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String locationString = rs.getString("lastback");
                    if (locationString != null) {
                        String[] parts = locationString.split(",");
                        if (parts.length == 6) {
                            return new Location(
                                    Bukkit.getWorld(parts[0]),
                                    Double.parseDouble(parts[1]),
                                    Double.parseDouble(parts[2]),
                                    Double.parseDouble(parts[3]),
                                    Float.parseFloat(parts[4]),
                                    Float.parseFloat(parts[5]));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error getting back location", e);
        }
        return null;
    }

    public static void setLastPosition(Player player, Location location) {
        String sql = "UPDATE user_settings SET lastback = ?, isback = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            String locationString = String.format("%s,%f,%f,%f,%f,%f",
                    Objects.requireNonNull(location.getWorld()).getName(),
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    location.getYaw(),
                    location.getPitch());
            stmt.setString(1, locationString);
            stmt.setBoolean(2, true);
            stmt.setString(3, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting back location", e);
        }
    }

    public static boolean isBack(Player player) {
        String sql = "SELECT isback FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isback");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error checking isback setting", e);
        }
        return false;
    }

    public static void setBack(Player player, boolean value) {
        String sql = "UPDATE user_settings SET isback = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, value);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting isback", e);
        }
    }

    public static boolean allowsFeedback(Player player) {
        String sql = "SELECT feedback FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("feedback");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error checking feedback setting", e);
        }
        return true;
    }

    public static void setFeedback(Player player, boolean value) {
        String sql = "UPDATE user_settings SET feedback = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, value);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting feedback", e);
        }
    }

    public static boolean allowsTPAs(Player player) {
        String sql = "SELECT allowtpas FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return !rs.getBoolean("allowtpas");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error checking allowtpas setting", e);
        }
        return false;
    }

    public static void setTPAs(Player player, boolean value) {
        String sql = "UPDATE user_settings SET allowtpas = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, value);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting allowtpas", e);
        }
    }

    public static boolean allowsVanishMessage(Player player) {
        String sql = "SELECT vanish_message FROM user_settings WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, player.getUniqueId().toString());
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("vanish_message");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error checking vanish message setting", e);
        }
        return true;
    }

    public static void setVanishMessage(Player player, boolean value) {
        String sql = "UPDATE user_settings SET vanish_message = ? WHERE uuid = ?";
        try (Connection conn = Config.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, value);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SettingsAPI.class.getName()).log(Level.SEVERE, "Error setting vanish message", e);
        }
    }

}
