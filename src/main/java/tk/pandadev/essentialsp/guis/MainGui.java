package tk.pandadev.essentialsp.guis;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.pandadev.essentialsp.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGui extends GUI {

    public MainGui(Player player) {
        super("Menu", 5);

        ////////////////////////////////////////////////////////////////////////////////////////////////

        ItemStack feedback_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8| §aCommand Feedback ist aktiviert")
                .addLoreLine("")
                .addLoreLine("§8Steuert ob eine Nachricht als ergebnis").
                addLoreLine("§8nach einem Command angezeigt werden soll")
                .addLoreLine("§8z.b. 'Du wurdest in den Gamemode Creative gesetzt'")
                .addLoreLine("§8Fehlermeldungen werden dennoch angezeigt")
                .addLoreLine("§8sowie wichtige nachrichten")
                .addLoreLine("")
                .addLoreLine("§aLinksklick §c>> §7Toggle")
                .build();

        ItemStack feedback_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8| §7Command Feedback ist deaktiviert")
                .addLoreLine("")
                .addLoreLine("§8Steuert ob eine Nachricht als ergebnis").
                addLoreLine("§8nach einem Command angezeigt werden soll")
                .addLoreLine("§8z.b. 'Du wurdest in den Gamemode Creative gesetzt'")
                .addLoreLine("§8Fehlermeldungen werden dennoch angezeigt")
                .addLoreLine("§8sowie wichtige nachrichten")
                .addLoreLine("")
                .addLoreLine("§aLinksklick §c>> §7Toggle")
                .build();

        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
            setItemClickEvent(33, player1 -> feedback_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".feedback", false);
                    Main.getInstance().saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(33, player1 -> feedback_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".feedback", true);
                    Main.getInstance().saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////

        setItem(15, player1 -> new ItemBuilder(Material.NETHERITE_INGOT).setName("§8Command Feedback Settings").build());

        ////////////////////////////////////////////////////////////////////////////////////////////////

        ItemStack vanish_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8| §aFake Join/Quit Message aktiviert")
                .addLoreLine("")
                .addLoreLine("§8Steuert ob eine Fake Join oder Quit").
                addLoreLine("§8Nachricht angezeigt werden soll")
                .addLoreLine("§8wen du in den Vanish gehst oder ihn verlässt")
                .addLoreLine("")
                .addLoreLine("§aLinksklick §c>> §7Toggle")
                .build();

        ItemStack vanish_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8| §7Fake Join/Quit Message deaktiviert")
                .addLoreLine("")
                .addLoreLine("§8Steuert ob eine Fake Join oder Quit").
                addLoreLine("§8Nachricht angezeigt werden soll")
                .addLoreLine("§8wen du in den Vanish gehst oder ihn verlässt")
                .addLoreLine("")
                .addLoreLine("§aLinksklick §c>> §7Toggle")
                .build();

        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".vanish." + "message")){
            setItemClickEvent(29, player1 -> vanish_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish" + ".message", false);
                    Main.getInstance().saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(29, player1 -> vanish_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Main.getInstance().getSettingsConfig().set(player.getUniqueId() + ".vanish" + ".message", true);
                    Main.getInstance().saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////

        setItem(11, player1 -> new ItemBuilder(Material.HEART_OF_THE_SEA).setName("§3Vanish Schittings").build());

        setItemClickEvent(13, player1 -> new ItemBuilder(Material.COMPASS).setName("§7Home Settings").build(), (player1, event) -> {
            new HomeGui(player1).open(player1);
        });

        setItemClickEvent(31, player1 -> new ItemBuilder(Material.RECOVERY_COMPASS).setName("§7Warp Settings").build(), (player1, event) -> {
            new WarpGui().open(player1);
        });

        setItem(20, player1 -> new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        setItem(24, player1 -> new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
