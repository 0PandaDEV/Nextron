package tk.pandadev.nextron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.SettingsConfig;
import tk.pandadev.nextron.utils.VanishAPI;

import java.util.UUID;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SettingsConfig.checkSettings(player);
        if (Configs.feature.getBoolean("join_leave_system")) {
            if (VanishAPI.isVanish(player)) {
                event.setQuitMessage(null);
                return;
            } else {
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("leave_message").replace("%p", Configs.settings.getString(player.getUniqueId() + ".nick"))));
            }
            if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))) {
                event.setQuitMessage(event.getQuitMessage() + " §8• §x§6§2§0§0§f§fNextron founder");
            }
            if (player.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac"))) {
                event.setQuitMessage(event.getQuitMessage() + " §8• §r§cWarrradu");
            }
        }
    }

}
