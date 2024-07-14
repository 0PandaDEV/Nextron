package net.pandadev.nextron.listeners;

import net.pandadev.nextron.apis.FeatureAPI;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.apis.SettingsAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEditor implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (FeatureAPI.getFeature("rank_system")) {
            if (RankAPI.getRanks().isEmpty()) {
                player.setDisplayName("§9Player §8• §f" + ChatColor.WHITE + SettingsAPI.getNick(player));
                event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
                return;
            }
            String rank = RankAPI.getRank(player);
            String prefix = RankAPI.getRankPrefix(rank);
            player.setDisplayName(prefix + ChatColor.WHITE + SettingsAPI.getNick(player));
        } else {
            player.setDisplayName(SettingsAPI.getNick(player));
        }
        event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
    }

}
