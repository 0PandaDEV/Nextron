package net.pandadev.nextron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;

public class ChatEditor implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        if (Configs.feature.getBoolean("rank_system")) {
            if (ranks == null){
                player.setDisplayName("§9Player §8• §f" + ChatColor.WHITE + Configs.settings.getString(player.getUniqueId() + ".nick"));
                event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
                return;
            }
            for (String rank : ranks.getKeys(false)){
                if (ranks.getStringList(rank + ".players").contains(player.getUniqueId().toString())) player.setDisplayName(ranks.getString(rank + ".prefix") + ChatColor.WHITE + Configs.settings.getString(player.getUniqueId() + ".nick"));
            }
        } else {
            player.setDisplayName(Configs.settings.getString(player.getUniqueId() + ".nick"));
        }
        event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
    }

}
