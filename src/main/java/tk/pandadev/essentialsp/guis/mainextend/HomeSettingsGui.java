package tk.pandadev.essentialsp.guis.mainextend;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.listeners.InputListener;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.Utils;

import java.util.Objects;

public class HomeSettingsGui extends GUI {

    public HomeSettingsGui(String home) {
        super("§7" + home, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ENDER_PEARL).setName("§x§0§1§5§9§5§6Teleport").build(), ((player, event) -> {
            player.teleport((Location) Objects.requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + "." + home)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.closeInventory();
        }));
        setItemClickEvent(13, player -> new ItemBuilder(Material.YELLOW_DYE).setName("§eRename").build(), ((player, event) -> {
            player.closeInventory();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_rename_request").replace("%h", home));
            InputListener.listen(player.getUniqueId(), new InputListener.InputListenerResponse() {
                @Override
                public void handle(AsyncPlayerChatEvent event) {
                    event.setCancelled(true);
                    Location location = (Location) Configs.home.get("Homes." + player.getUniqueId() + "." + home);
                    Configs.home.set("Homes." + player.getUniqueId() + "." + event.getMessage(), location);
                    Configs.home.set("Homes." + player.getUniqueId() + "." + home, null);
                    Configs.saveHomeConfig();
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_rename_success").replace("%h", home).replace("%n", event.getMessage()));
                }
            });
        }));
        setItemClickEvent(14, player -> new ItemBuilder(Material.RED_DYE).setName("§cDelete").build(), ((player, event) -> {
            Configs.home.set("Homes." + player.getUniqueId() + "." + home, null);
            Main.getInstance().saveConfig();
            if (Configs.home.getConfigurationSection("Homes." + player.getUniqueId()).getKeys(false).isEmpty()) {
                player.closeInventory();
            } else {
                new HomeManagerGui(player).open(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new HomeManagerGui(player1).open(player1);
        });
    }
}
