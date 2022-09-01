package tk.pandadev.essentialsp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputListener implements Listener {

    private static final Map<UUID, InputListenerResponse> listenerMap = new HashMap<>();

    public static void listen(UUID uuid, InputListenerResponse response) {
        listenerMap.putIfAbsent(uuid, response);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (listenerMap.containsKey(uuid)) {
            InputListenerResponse response = listenerMap.get(uuid);
            response.handle(event);
            listenerMap.remove(uuid);
        }
    }


    public interface InputListenerResponse {
        void handle(AsyncPlayerChatEvent event);

    }

}