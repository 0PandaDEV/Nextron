package net.pandadev.nextron.apis;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.database.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarpAPI {
    private static final Logger LOGGER = Logger.getLogger(WarpAPI.class.getName());

    public static void setWarp(String warpName, Location location) {
        String sql = "INSERT OR REPLACE INTO warps (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, warpName);
            ps.setString(2, location.getWorld().getName());
            ps.setDouble(3, location.getX());
            ps.setDouble(4, location.getY());
            ps.setDouble(5, location.getZ());
            ps.setFloat(6, location.getYaw());
            ps.setFloat(7, location.getPitch());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting warp: " + warpName, e);
        }
    }

    public static void deleteWarp(String warpName) {
        String sql = "DELETE FROM warps WHERE name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, warpName);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting warp: " + warpName, e);
        }
    }

    public static Location getWarp(String warpName) {
        String sql = "SELECT world, x, y, z, yaw, pitch FROM warps WHERE name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, warpName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Location(
                            Bukkit.getWorld(rs.getString("world")),
                            rs.getDouble("x"),
                            rs.getDouble("y"),
                            rs.getDouble("z"),
                            rs.getFloat("yaw"),
                            rs.getFloat("pitch")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting warp: " + warpName, e);
        }
        return null;
    }

    public static List<String> getWarps() {
        List<String> warps = new ArrayList<>();
        String sql = "SELECT name FROM warps ORDER BY name";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                warps.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting warps", e);
        }
        return warps;
    }

    public static void migration() {
        String checkSql = "SELECT COUNT(*) FROM warps";
        try (PreparedStatement checkPs = Config.getConnection().prepareStatement(checkSql);
             ResultSet rs = checkPs.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking warps table", e);
            return;
        }

        File warpConfig = new File(Main.getInstance().getDataFolder(), "warps.yml");
        if (!warpConfig.exists()) {
            return;
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(warpConfig);
        ConfigurationSection warpsSection = yamlConfig.getConfigurationSection("Warps");

        if (warpsSection == null) {
            return;
        }

        String sql = "INSERT INTO warps (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            for (String warpName : warpsSection.getKeys(false)) {
                Location loc = warpsSection.getLocation(warpName);
                if (loc != null) {
                    ps.setString(1, warpName);
                    ps.setString(2, loc.getWorld().getName());
                    ps.setDouble(3, loc.getX());
                    ps.setDouble(4, loc.getY());
                    ps.setDouble(5, loc.getZ());
                    ps.setFloat(6, loc.getYaw());
                    ps.setFloat(7, loc.getPitch());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            LOGGER.info("Warp data migration completed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error migrating warp data to database", e);
        }
    }
}