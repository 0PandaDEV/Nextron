package tk.pandadev.essentialsp.utils;

import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

public class SettingsConfig {
    public static void checkSettings(Player player){
        if (!Main.getInstance().getSettingsConfig().getConfigurationSection("").getKeys(false).contains(String.valueOf(player.getUniqueId()))){
            setSettings(player);
        } else if (!Main.getInstance().getSettingsConfig().getConfigurationSection(String.valueOf(player.getUniqueId())).getKeys(false).contains("vanish")){
            setSettings(player);
        } else if (!Main.getInstance().getSettingsConfig().getConfigurationSection(String.valueOf(player.getUniqueId())).getKeys(false).contains("feedback")){
            setSettings(player);
        }

    }

    private static void setSettings(Player player){
        Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish." + "message", true);
        Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".feedback", true);
        Main.getInstance().saveSettingsConfig();
    }

}
