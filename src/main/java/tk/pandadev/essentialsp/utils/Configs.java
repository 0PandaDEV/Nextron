package tk.pandadev.essentialsp.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.pandadev.essentialsp.Main;

import java.io.File;
import java.io.IOException;

public class Configs {

    public static File settingsConfig;
    public static FileConfiguration settings;
    public static File homeConfig;
    public static FileConfiguration home;
    public static File warpConfig;
    public static FileConfiguration warp;

    public static void createSettingsConfig() {
        settingsConfig = new File(Main.getInstance().getDataFolder(), "user_settings.yml");
        if (!settingsConfig.exists()) {
            settingsConfig.getParentFile().mkdirs();
            Main.getInstance().saveResource("user_settings.yml", false);
        }

        settings = new YamlConfiguration();
        try {
            settings.load(settingsConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveSettingsConfig() {
        try{
            settings.save(settingsConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createHomeConfig() {
        homeConfig = new File(Main.getInstance().getDataFolder(), "homes.yml");
        if (!homeConfig.exists()) {
            homeConfig.getParentFile().mkdirs();
            Main.getInstance().saveResource("homes.yml", false);
        }

        home = new YamlConfiguration();
        try {
            home.load(homeConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveHomeConfig() {
        try{
            home.save(homeConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createWarpConfig() {
        warpConfig = new File(Main.getInstance().getDataFolder(), "warps.yml");
        if (!warpConfig.exists()) {
            warpConfig.getParentFile().mkdirs();
            Main.getInstance().saveResource("warps.yml", false);
        }

        warp = new YamlConfiguration();
        try {
            warp.load(warpConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveWarpConfig() {
        try{
            warp.save(warpConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
            
}
