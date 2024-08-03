package net.pandadev.nextron.listeners;

import net.pandadev.nextron.Main;
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
                String playerRankPrefix = Main.getInstance().getConfig().getString("playerRankPrefix");
                player.setDisplayName(playerRankPrefix + ChatColor.WHITE + SettingsAPI.getNick(player));
            } else {
                String rank = RankAPI.getRank(player);
                String prefix = RankAPI.getRankPrefix(rank);
                player.setDisplayName(prefix + ChatColor.WHITE + SettingsAPI.getNick(player));
            }
        } else {
            player.setDisplayName(SettingsAPI.getNick(player));
        }

        if (FeatureAPI.getFeature("chat_formatting_system")) {
            String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);
            event.setFormat("%1$s §8» §f%2$s");
            event.setMessage(formattedMessage);
        }
    }

}