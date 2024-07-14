package net.pandadev.nextron.listeners;

import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.SettingsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackCommandListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        SettingsAPI.setLastPosition(player, player.getLocation());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (SettingsAPI.isBack(player)) {
            SettingsAPI.setBack(player, false);
            return;
        }
        SettingsAPI.setLastPosition(player, player.getLocation());
    }

}
