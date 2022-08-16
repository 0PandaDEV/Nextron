package tk.pandadev.essentialsp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.pandadev.essentialsp.Main;

import java.util.ArrayList;

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

    public static boolean isVanish(Player player){
        return Main.getInstance().getConfig().getBoolean("Vanished." + player.getUniqueId());
    }

    private void executeVanish(Player player){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            System.out.println(isVanish(player));
            if (isVanish(player)) {
                onlinePlayer.hidePlayer(this.plugin, player);
                System.out.println("asdasd");
                continue;
            }
            onlinePlayer.showPlayer(this.plugin, player);
        }
    }

}
