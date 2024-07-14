package net.pandadev.nextron.apis;

import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishAPI {

    public static void setVanish(Player player, Boolean state) {
        SettingsAPI.setVanished(player, state);
        executeVanish(player);
    }

    public static boolean isVanish(Player player) {
        return SettingsAPI.getVanished(player);
    }

    public static void executeVanish(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (isVanish(player)) {
                onlinePlayer.hidePlayer(Main.getInstance(), player);
                continue;
            }
            onlinePlayer.showPlayer(Main.getInstance(), player);
        }
    }

}
