package net.pandadev.nextron.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.pandadev.nextron.Main;

public class VanishAPI {

    private final Plugin plugin;

    public VanishAPI(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setVanish(Player player, Boolean state) {
        Main.getInstance().getConfig().set("Vanished." + player.getUniqueId(), state);
        Main.getInstance().saveConfig();
        executeVanish(player);
    }

    public static boolean isVanish(Player player) {
        return Main.getInstance().getConfig().getBoolean("Vanished." + player.getUniqueId());
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
