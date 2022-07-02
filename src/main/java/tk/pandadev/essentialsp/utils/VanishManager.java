package tk.pandadev.essentialsp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {
    private final Plugin plugin;
    private final List<Player> vanished;

    public VanishManager(Plugin plugin) {
        this.plugin = plugin;
        this.vanished = new ArrayList<Player>();
    }

    public List<Player> getVanished() {
        return this.vanished;
    }

    public boolean isVanished(Player player) {
        return this.vanished.contains(player);
    }

    public void setVanished(Player player, boolean bool) {
        if (bool) {
            this.vanished.add(player);
        } else {
            this.vanished.remove(player);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (player.equals(onlinePlayer)) continue;
            if (bool) {
                onlinePlayer.hidePlayer(this.plugin, player);
                continue;
            }
            onlinePlayer.showPlayer(this.plugin, player);
        }
    }

    public void hideAll(Player player) {
        if (!player.isOp()){
            this.vanished.forEach(player1 -> player.hidePlayer(this.plugin, (Player)player1));
        }
    }

}
