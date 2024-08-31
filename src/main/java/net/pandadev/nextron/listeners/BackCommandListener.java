package net.pandadev.nextron.listeners;

import net.pandadev.nextron.apis.SettingsAPI;
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
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.EXIT_BED && event.getCause() != PlayerTeleportEvent.TeleportCause.DISMOUNT) {
            if (!SettingsAPI.getBack(player)) {
                SettingsAPI.setLastPosition(player, event.getFrom());
            }
        }
        if (SettingsAPI.getBack(event.getPlayer())) SettingsAPI.setBack(event.getPlayer(), false);
    }
}
