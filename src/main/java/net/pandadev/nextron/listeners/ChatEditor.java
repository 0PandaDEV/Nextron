package net.pandadev.nextron.listeners;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEditor implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        if (Configs.feature.getBoolean("rank_system")) {
            if (ranks == null) {
                player.setDisplayName("§9Player §8• §f" + ChatColor.WHITE + SettingsAPI.getNick(player));
                event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
                return;
            }
            for (String rank : ranks.getKeys(false)) {
                if (ranks.getStringList(rank + ".players").contains(player.getUniqueId().toString()))
                    player.setDisplayName(ranks.getString(rank + ".prefix") + ChatColor.WHITE + SettingsAPI.getNick(player));
            }
        } else {
            player.setDisplayName(SettingsAPI.getNick(player));
        }
        event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
    }

}
