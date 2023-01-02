package tk.pandadev.essentialsp.guis.mainextend;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.Utils;

public class PlayerSettingsGui extends GUI {

    public PlayerSettingsGui(Player player) {
        super("§7Settings", 3);

        ItemStack feedback_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Command feedback")
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
                .setName("§c❌ §8• §7Command Feedback")
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
            setItemClickEvent(12, player1 -> feedback_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", false);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(12, player1 -> feedback_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", true);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        //////////////////////////////////////////

        ItemStack vanish_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Fake Join/Quit Message")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_3"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_4"))
                .build();

        ItemStack vanish_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Fake Join/Quit Message")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_1"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_2"))
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_3"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("maingui_vanish_lore_4"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")){
            setItemClickEvent(14, player1 -> vanish_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", false);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(14, player1 -> vanish_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", true);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new MainGui(player1).open(player1);
        });
    }

}
