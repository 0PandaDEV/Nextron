package tk.pandadev.essentialsp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.SettingsConfig;
import tk.pandadev.essentialsp.utils.VanishAPI;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SettingsConfig.checkSettings(player);
        if (VanishAPI.isVanish(player)){
            event.setQuitMessage("");
        }else {
            event.setQuitMessage(Main.getInstance().getConfig().getString("leave_message").replace("%p", player.getName()));
        }
    }

}
