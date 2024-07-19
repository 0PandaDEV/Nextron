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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeAPI {
    private static final Logger LOGGER = Logger.getLogger(HomeAPI.class.getName());

    public static void setHome(Player player, String homeName, Location location) {
        String sql = "INSERT OR REPLACE INTO homes (uuid, name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, homeName);
            ps.setString(3, location.getWorld().getName());
            ps.setDouble(4, location.getX());
            ps.setDouble(5, location.getY());
            ps.setDouble(6, location.getZ());
            ps.setFloat(7, location.getYaw());
            ps.setFloat(8, location.getPitch());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting home for player: " + player.getName(), e);
        }
    }

    public static void deleteHome(Player player, String homeName) {
        String sql = "DELETE FROM homes WHERE uuid = ? AND name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, homeName);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting home for player: " + player.getName(), e);
        }
    }

    public static void renameHome(Player player, String oldName, String newName) {
        String sql = "UPDATE homes SET name = ? WHERE uuid = ? AND name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, player.getUniqueId().toString());
            ps.setString(3, oldName);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error renaming home for player: " + player.getName(), e);
        }
    }

    public static Location getHome(Player player, String homeName) {
        String sql = "SELECT world, x, y, z, yaw, pitch FROM homes WHERE uuid = ? AND name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, homeName);
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
            LOGGER.log(Level.SEVERE, "Error getting home for player: " + player.getName(), e);
        }
        return null;
    }

    public static List<String> getHomes(Player player) {
        List<String> homes = new ArrayList<>();
        String sql = "SELECT name FROM homes WHERE uuid = ? ORDER BY name";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    homes.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting homes for player: " + player.getName(), e);
        }
        return homes;
    }

    public static void migration() {
        File homeConfig = new File(Main.getInstance().getDataFolder(), "homes.yml");
        if (!homeConfig.exists()) {
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM homes";
        try (PreparedStatement checkPs = Config.getConnection().prepareStatement(checkSql);
             ResultSet rs = checkPs.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking homes table", e);
            return;
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(homeConfig);
        ConfigurationSection homesSection = yamlConfig.getConfigurationSection("Homes");

        if (homesSection == null) {
            return;
        }

        String sql = "INSERT INTO homes (uuid, name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            for (String uuid : homesSection.getKeys(false)) {
                ConfigurationSection playerHomes = homesSection.getConfigurationSection(uuid);
                if (playerHomes != null) {
                    for (String homeName : playerHomes.getKeys(false)) {
                        Location loc = playerHomes.getLocation(homeName);
                        if (loc != null) {
                            ps.setString(1, uuid);
                            ps.setString(2, homeName);
                            ps.setString(3, loc.getWorld().getName());
                            ps.setDouble(4, loc.getX());
                            ps.setDouble(5, loc.getY());
                            ps.setDouble(6, loc.getZ());
                            ps.setFloat(7, loc.getYaw());
                            ps.setFloat(8, loc.getPitch());
                            ps.addBatch();
                        }
                    }
                }
            }
            ps.executeBatch();
            LOGGER.info("Home data migration completed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error migrating home data to database", e);
        }
    }
}