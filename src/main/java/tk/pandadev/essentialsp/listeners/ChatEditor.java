package tk.pandadev.essentialsp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.pandadev.essentialsp.Main;

import java.util.Objects;

public class ChatEditor implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        for (String rank : ranks.getKeys(false)){
            if (ranks.getStringList(rank + ".players").contains(player.getUniqueId().toString())) player.setDisplayName(ranks.getString(rank + ".prefix") + ChatColor.WHITE + player.getName());
        }
        event.setFormat(player.getDisplayName() + " §8» §f" + ChatColor.translateAlternateColorCodes('&', message));
    }

}
