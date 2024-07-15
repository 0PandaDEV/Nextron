package net.pandadev.nextron.apis;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.database.Config;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeatureAPI {
    private static final Logger LOGGER = Logger.getLogger(FeatureAPI.class.getName());
    private static final Map<String, Boolean> featureCache = new HashMap<>();

    public static void setFeature(String featureName, boolean state) {
        String sql = "INSERT OR REPLACE INTO features (name, state) VALUES (?, ?)";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, featureName);
            ps.setBoolean(2, state);
            ps.executeUpdate();
            featureCache.put(featureName, state);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting feature state: " + featureName, e);
        }
    }

    public static boolean getFeature(String featureName) {
        if (featureCache.containsKey(featureName)) {
            return featureCache.get(featureName);
        }

        String sql = "SELECT state FROM features WHERE name = ?";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, featureName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean state = rs.getBoolean("state");
                    featureCache.put(featureName, state);
                    return state;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting feature state: " + featureName, e);
        }
        return false;
    }

    public static void migration() {
        String checkSql = "SELECT COUNT(*) FROM features";
        try (PreparedStatement checkPs = Config.getConnection().prepareStatement(checkSql);
             ResultSet rs = checkPs.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                LOGGER.info("Features table is not empty. Skipping migration.");
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking features table", e);
            return;
        }

        File featureConfig = new File(Main.getInstance().getDataFolder(), "features.yml");
        if (!featureConfig.exists()) {
            LOGGER.info("No features.yml file found. Skipping migration.");
            return;
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(featureConfig);
        String sql = "INSERT OR REPLACE INTO features (name, state) VALUES (?, ?)";

        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            for (String key : yamlConfig.getKeys(false)) {
                boolean state = yamlConfig.getBoolean(key);
                ps.setString(1, key);
                ps.setBoolean(2, state);
                ps.addBatch();
            }
            ps.executeBatch();
            LOGGER.info("Feature data migration completed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error migrating feature data to database", e);
        }
    }
}
