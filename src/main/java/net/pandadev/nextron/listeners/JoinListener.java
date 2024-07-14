package net.pandadev.nextron.listeners;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.apis.VanishAPI;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SettingsAPI.initializeUser(player);

        if (Configs.feature.getBoolean("join_leave_system")) {
            if (VanishAPI.isVanish(player)) {
                event.setJoinMessage("");
            } else {
                event.setJoinMessage(
                        ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("join_message")
                                .replace("%p", SettingsAPI.getNick(player))));
            }
            if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0")) && !VanishAPI.isVanish(player)) {
                event.setJoinMessage(event.getJoinMessage() + " §8• §x§6§2§0§0§f§fNextron founder");
            }
            if (player.getUniqueId().equals(UUID.fromString("51666aba-5e87-40c4-900c-1c77ce0b8e3c")) && !VanishAPI.isVanish(player)) {
                event.setJoinMessage(event.getJoinMessage() + " §8• §x§f§f§6§5§f§aAnya");
            }
            if (player.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac")) && !VanishAPI.isVanish(player)) {
                event.setJoinMessage(event.getJoinMessage() + " §8• §r§cWarrradu");
            }
        }

        RankAPI.createPlayerTeam(player);
        RankAPI.checkRank(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            VanishAPI.executeVanish(onlinePlayer);
        }
    }

}
