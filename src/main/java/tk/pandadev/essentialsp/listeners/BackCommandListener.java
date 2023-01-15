package tk.pandadev.essentialsp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import tk.pandadev.essentialsp.utils.Configs;

public class BackCommandListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Configs.settings.set(player.getUniqueId() + ".lastpos", player.getLocation());
        Configs.saveSettingsConfig();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Configs.settings.set(player.getUniqueId() + ".lastpos", player.getLocation());
        Configs.saveSettingsConfig();
    }

}
