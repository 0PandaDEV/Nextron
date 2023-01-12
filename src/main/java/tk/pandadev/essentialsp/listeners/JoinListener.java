package tk.pandadev.essentialsp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.RankAPI;
import tk.pandadev.essentialsp.utils.SettingsConfig;
import tk.pandadev.essentialsp.utils.VanishAPI;

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
        if (player.getUniqueId().equals("2dae5251-257a-4d28-b220-60fe24de72f0")){
            event.setJoinMessage(event.getJoinMessage() + " §8• §x§6§2§0§0§f§fPlugin Creator");
            for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                onlineplayer.playSound(onlineplayer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 100, 1);
            }
        }
        RankAPI.createPlayerTeam(player);
        RankAPI.checkRank(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

}
