package tk.pandadev.essentialsp.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.guis.FeedbackGui;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.guis.VanishGui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryClickListener implements Listener {

    private List<GameMode> gameModes = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) throws NoSuchFieldException, IllegalAccessException {
        Player player = (Player)(event.getWhoClicked());
        if (event.getView().getTitle().equals("Settings")) {
            event.setCancelled(true);

            if (Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem().getItemMeta())).hasLocalizedName()){
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                    case "vanish settings":
                        player.openInventory(VanishGui.getInventory(player));
                        break;
                    case "feedback settings":
                        player.openInventory(FeedbackGui.getInventory(player));
                        break;
                    case "vanish message toggler":
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".vanish." + "message")){
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish." + "message", false);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(MainGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                        } else {
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish." + "message", true);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(MainGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                        }
                        break;
                    case "feedback toggler":
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".feedback", false);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(MainGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                        } else {
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".feedback", true);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(MainGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                        }
                        break;
                }
            }
        } else if (event.getView().getTitle().equals("§c§lVanish Settings")){
            event.setCancelled(true);

            if (Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).hasLocalizedName()){
                switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                    case "vanish_message_switcher":
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".vanish." + "message")){
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish." + "message", false);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(VanishGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                        } else {
                            Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish." + "message", true);
                            Main.getInstance().saveSettingsConfig();
                            player.openInventory(VanishGui.getInventory(player));
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                        }
                        break;
                }
            }
        } else {
            event.setCancelled(false);
        }
        if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
            if (event.getCurrentItem().getItemMeta().getLocalizedName().equals("back_to_main")){
                player.openInventory(MainGui.getInventory(player));
            }
        }
    }
}
