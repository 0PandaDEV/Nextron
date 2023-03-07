package tk.pandadev.essentialsp.utils;

import org.bukkit.entity.Player;

public class SettingsConfig {
    public static void checkSettings(Player player){
        if (!Configs.settings.getConfigurationSection("").getKeys(false).contains(String.valueOf(player.getUniqueId()))){
            setSettings(player);
        } else if (!Configs.settings.getConfigurationSection(String.valueOf(player.getUniqueId())).getKeys(false).contains("vanish")){
            setSettings(player);
        } else if (!Configs.settings.getConfigurationSection(String.valueOf(player.getUniqueId())).getKeys(false).contains("feedback")){
            setSettings(player);
        } else if (!Configs.settings.getConfigurationSection(String.valueOf(player.getUniqueId())).getKeys(false).contains("allowtpas")){
            setSettings(player);
        }

    }

    private static void setSettings(Player player){
        Configs.settings.set(player.getUniqueId() + ".vanish." + "message", true);
        Configs.settings.set(player.getUniqueId() + ".feedback", true);
        Configs.settings.set(player.getUniqueId() + ".allowtpas", true);
        Configs.saveSettingsConfig();
    }

}
