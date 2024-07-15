package net.pandadev.nextron.apis;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.database.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsAPI {
    private static final Logger LOGGER = Logger.getLogger(SettingsAPI.class.getName());

    public static void initializeUser(Player player) {
        String checkSql = "SELECT COUNT(*) FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        String insertSql = "INSERT INTO user_settings (uuid, vanish_message, vanish_vanished, feedback, allowtpas, nick) VALUES ('"
                +
                player.getUniqueId() + "', 1, 0, 1, 1, '" + player.getName() + "')";

        try {
            Config.executeQuery(checkSql, rs -> {
                if (rs.next() && rs.getInt(1) == 0) {
                    Config.executeUpdate(insertSql);
                }
            });
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error initializing user: " + player.getName(), e);
        }
    }

    public static String getNick(Player player) {
        String sql = "SELECT nick FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final String[] nick = {player.getName()};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    nick[0] = rs.getString("nick");
                }
            });
            return nick[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting nick: " + player.getName(), e);
        }
        return player.getName();
    }

    public static void setNick(Player player, String nick) {
        String sql = "UPDATE user_settings SET nick = '" + nick + "' WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting nick: " + player.getName(), e);
        }
    }

    public static Location getLastPosition(Player player) {
        String sql = "SELECT lastback FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final Location[] location = {player.getLocation()};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    String locationString = rs.getString("lastback");
                    if (locationString != null) {
                        String[] parts = locationString.split(",");
                        if (parts.length == 6) {
                            location[0] = new Location(
                                    Bukkit.getWorld(parts[0]),
                                    Double.parseDouble(parts[1]),
                                    Double.parseDouble(parts[2]),
                                    Double.parseDouble(parts[3]),
                                    Float.parseFloat(parts[4]),
                                    Float.parseFloat(parts[5]));
                            LOGGER.info("Last position for " + player.getName() + ": " + location[0]);
                        }
                    }
                }
            });
            return location[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting last position: " + player.getName(), e);
            return player.getLocation();
        }
    }

    public static void setLastPosition(Player player, Location location) {
        String locationString = String.format("%s,%f,%f,%f,%f,%f",
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
        String sql = "UPDATE user_settings SET lastback = '" + locationString + "' WHERE uuid = '"
                + player.getUniqueId() + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting last position: " + player.getName(), e);
        }
    }

    public static boolean getBack(Player player) {
        String sql = "SELECT isback FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final boolean[] isBack = {false};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    isBack[0] = rs.getBoolean("isback");
                }
            });
            return isBack[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking isBack: " + player.getName(), e);
        }
        return false;
    }

    public static void setBack(Player player, boolean value) {
        String sql = "UPDATE user_settings SET isback = " + (value ? 1 : 0) + " WHERE uuid = '" + player.getUniqueId()
                + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting isBack: " + player.getName(), e);
        }
    }

    public static boolean allowsFeedback(Player player) {
        String sql = "SELECT feedback FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final boolean[] feedback = {true};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    feedback[0] = rs.getBoolean("feedback");
                }
            });
            return feedback[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking feedback: " + player.getName(), e);
        }
        return true;
    }

    public static void setFeedback(Player player, boolean value) {
        String sql = "UPDATE user_settings SET feedback = " + (value ? 1 : 0) + " WHERE uuid = '" + player.getUniqueId()
                + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting feedback: " + player.getName(), e);
        }
    }

    public static boolean allowsTPAs(Player player) {
        String sql = "SELECT allowtpas FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final boolean[] allowTPAs = {true};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    allowTPAs[0] = rs.getBoolean("allowtpas");
                }
            });
            return allowTPAs[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking allowTPAs: " + player.getName(), e);
        }
        return true;
    }

    public static void setTPAs(Player player, boolean value) {
        String sql = "UPDATE user_settings SET allowtpas = " + (value ? 1 : 0) + " WHERE uuid = '"
                + player.getUniqueId() + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting allowTPAs: " + player.getName(), e);
        }
    }

    public static boolean allowsVanishMessage(Player player) {
        String sql = "SELECT vanish_message FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final boolean[] vanishMessage = {true};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    vanishMessage[0] = rs.getBoolean("vanish_message");
                }
            });
            return vanishMessage[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking vanish message: " + player.getName(), e);
        }
        return true;
    }

    public static void setVanishMessage(Player player, boolean value) {
        String sql = "UPDATE user_settings SET vanish_message = " + (value ? 1 : 0) + " WHERE uuid = '"
                + player.getUniqueId() + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting vanish message: " + player.getName(), e);
        }
    }

    public static void setVanished(Player player, boolean value) {
        String sql = "UPDATE user_settings SET vanish_vanished = " + (value ? 1 : 0) + " WHERE uuid = '"
                + player.getUniqueId() + "'";
        try {
            Config.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting vanished status: " + player.getName(), e);
        }
    }

    public static boolean getVanished(Player player) {
        String sql = "SELECT vanish_vanished FROM user_settings WHERE uuid = '" + player.getUniqueId() + "'";
        try {
            final boolean[] vanished = {false};
            Config.executeQuery(sql, rs -> {
                if (rs.next()) {
                    vanished[0] = rs.getBoolean("vanish_vanished");
                }
            });
            return vanished[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting vanished status: " + player.getName(), e);
        }
        return false;
    }

    public static void migration() {
        String checkSql = "SELECT COUNT(*) FROM user_settings";
        try (PreparedStatement checkPs = Config.getConnection().prepareStatement(checkSql);
             ResultSet rs = checkPs.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking user_settings table", e);
            return;
        }

        File settingsConfig = new File(Main.getInstance().getDataFolder(), "user_settings.yml");
        if (!settingsConfig.exists()) {
            return;
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(settingsConfig);
        String sql = "INSERT INTO user_settings (uuid, vanish_message, vanish_vanished, feedback, allowtpas, nick, lastback, isback) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            for (String uuid : yamlConfig.getKeys(false)) {
                ConfigurationSection userSection = yamlConfig.getConfigurationSection(uuid);
                if (userSection != null) {
                    ps.setString(1, uuid);
                    ps.setBoolean(2, userSection.getBoolean("vanish.message", true));
                    ps.setBoolean(3, userSection.getBoolean("vanish.vanished", false));
                    ps.setBoolean(4, userSection.getBoolean("feedback", true));
                    ps.setBoolean(5, userSection.getBoolean("allowtpas", true));
                    ps.setString(6, userSection.getString("nick", ""));

                    Location lastback = userSection.getLocation("lastback");
                    ps.setString(7, lastback != null ? lastback.serialize().toString() : "");

                    ps.setBoolean(8, userSection.getBoolean("isback", false));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            LOGGER.info("User settings data migration completed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error migrating user settings data to database", e);
        }
    }
}