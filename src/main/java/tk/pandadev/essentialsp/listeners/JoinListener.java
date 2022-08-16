package tk.pandadev.essentialsp.listeners;

import org.bukkit.ChatColor;
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
            event.setJoinMessage("§2[§a+§2] " + ChatColor.GRAY + player.getName());
        }
        RankAPI.checkRank(player);
        run();
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
