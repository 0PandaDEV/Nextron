package net.pandadev.nextron.apis;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanishAPI {

    private static final String VANISH_MESSAGE = "§7You are currently in vanish";
    private static final Map<UUID, BukkitTask> vanishTasks = new HashMap<>();

    public static void setVanish(Player player, Boolean state) {
        SettingsAPI.setVanished(player, state);
        executeVanish(player);
        if (state) {
            startVanishActionBar(player);
        } else {
            stopVanishActionBar(player);
        }
    }

    public static boolean isVanish(Player player) {
        return SettingsAPI.getVanished(player);
    }

    public static void executeVanish(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (isVanish(player)) {
                onlinePlayer.hidePlayer(Main.getInstance(), player);
            } else {
                onlinePlayer.showPlayer(Main.getInstance(), player);
            }
        }
        if (isVanish(player) && !vanishTasks.containsKey(player.getUniqueId())) {
            startVanishActionBar(player);
        }
    }

    private static void startVanishActionBar(Player player) {
        UUID playerUUID = player.getUniqueId();
        stopVanishActionBar(player);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            Player onlinePlayer = Bukkit.getPlayer(playerUUID);
            if (onlinePlayer != null && onlinePlayer.isOnline() && isVanish(onlinePlayer)) {
                onlinePlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(VANISH_MESSAGE));
            } else {
                stopVanishActionBar(onlinePlayer);
            }
        }, 0L, 20L);
        vanishTasks.put(playerUUID, task);
    }

    private static void stopVanishActionBar(Player player) {
        if (player != null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
            BukkitTask task = vanishTasks.remove(player.getUniqueId());
            if (task != null) {
                task.cancel();
            }
        }
    }
}
