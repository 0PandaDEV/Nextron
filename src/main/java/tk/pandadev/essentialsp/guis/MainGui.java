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
import tk.pandadev.essentialsp.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        ItemStack feedback_inactive = new ItemBuilder(Material.LIME_DYE)
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

        setItem(11, player1 -> new ItemBuilder(Material.HEART_OF_THE_SEA).setName("§3Vanish Settings").build());

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
