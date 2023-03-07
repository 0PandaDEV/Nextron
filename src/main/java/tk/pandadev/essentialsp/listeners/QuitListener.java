package tk.pandadev.essentialsp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.SettingsConfig;
import tk.pandadev.essentialsp.utils.VanishAPI;

import java.util.UUID;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SettingsConfig.checkSettings(player);
        if (VanishAPI.isVanish(player)){
            event.setQuitMessage("");
        }else {
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("leave_message").replace("%p", player.getName())));
        }
        if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))){
            event.setQuitMessage(event.getQuitMessage() + " §8• §x§6§2§0§0§f§fEssentialsP Plugin Creator");
        }
    }

}
