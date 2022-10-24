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
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class MainGui extends GUI {

    public MainGui(Player player) {
        super("Menu", 5);

        ////////////////////////////////////////////////////////////////////////////////////////////////

        ItemStack feedback_active = new ItemBuilder(Material.LIME_DYE)
                .setName(LanguageLoader.translationMap.get("maingui_feedback_active_title"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_3"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_4"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_5"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_6"))
                .build();

        ItemStack feedback_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName(LanguageLoader.translationMap.get("maingui_feedback_inactive_title"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_3"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_4"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_5"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_feedback_lore_6"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
            setItemClickEvent(33, player1 -> feedback_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", false);
                    Configs.saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(33, player1 -> feedback_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", true);
                    Configs.saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////

        setItem(15, player1 -> new ItemBuilder(Material.NETHERITE_INGOT).setName("§8Command Feedback Settings").build());

        ////////////////////////////////////////////////////////////////////////////////////////////////

        ItemStack vanish_active = new ItemBuilder(Material.LIME_DYE)
                .setName(LanguageLoader.translationMap.get("maingui_vanish_active_title"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_3"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_4"))
                .build();

        ItemStack vanish_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName(LanguageLoader.translationMap.get("maingui_vanish_inactive_title"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_3"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_4"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")){
            setItemClickEvent(29, player1 -> vanish_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", false);
                    Configs.saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(29, player1 -> vanish_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", true);
                    Configs.saveSettingsConfig();
                    new MainGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////

        setItem(11, player1 -> new ItemBuilder(Material.HEART_OF_THE_SEA).setName("§3Vanish Settings").build());

        setItemClickEvent(13, player1 -> new ItemBuilder(Material.COMPASS).setName("§7Homes").build(), (player1, event) -> {
            if (Configs.home.getConfigurationSection("Homes") == null || Configs.home.getConfigurationSection("Homes").getKeys(false).isEmpty()){
                player1.closeInventory();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("maingui_no_homes"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new MainGui(player1).open(player1);
            } else {
                new HomeGui(player1).open(player1);
            }
        });

        setItemClickEvent(31, player1 -> new ItemBuilder(Material.RECOVERY_COMPASS).setName("§7Warps").build(), (player1, event) -> {
            if (Configs.warp.getConfigurationSection("Warps") == null || Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()){
                player1.closeInventory();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("maingui_no_warps"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new MainGui(player1).open(player1);
            } else {
                new WarpGui().open(player1);
            }
        });

        setItem(20, player1 -> new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        setItem(24, player1 -> new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
