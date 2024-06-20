package net.pandadev.nextron.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class InputListener implements Listener {
    private static final Map<UUID, CompletableFuture<String>> listenerMap = new HashMap<>();

    public static CompletableFuture<String> listen(UUID uuid) {
        CompletableFuture<String> future = new CompletableFuture<>();
        listenerMap.put(uuid, future);
        return future;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (listenerMap.containsKey(uuid)) {
            event.setCancelled(true);
            CompletableFuture<String> future = listenerMap.remove(uuid);
            future.complete(event.getMessage());
        }
    }
}