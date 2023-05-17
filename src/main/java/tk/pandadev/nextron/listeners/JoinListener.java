package tk.pandadev.nextron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.SettingsConfig;
import tk.pandadev.nextron.utils.VanishAPI;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        SettingsConfig.checkSettings(player);
        if (VanishAPI.isVanish(player)){
            event.setJoinMessage("");
        }else {
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("join_message").replace("%p", player.getName())));
        }
        if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))){
            event.setJoinMessage(event.getJoinMessage() + " §8• §x§6§2§0§0§f§fNextron Plugin Creator");
        }
        RankAPI.createPlayerTeam(player);
        RankAPI.checkRank(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

}
