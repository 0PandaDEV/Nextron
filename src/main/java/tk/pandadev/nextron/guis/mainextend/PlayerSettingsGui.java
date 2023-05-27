package tk.pandadev.nextron.guis.mainextend;

import ch.hekates.languify.language.Text;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.guis.MainGui;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.Utils;

public class PlayerSettingsGui extends GUI {

    public PlayerSettingsGui(Player player) {
        super("§7Settings", 3);

        ItemStack feedback_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Command feedback")
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.feedback.lore.1"))
                .addLoreLine(Text.get("maingui.feedback.lore.2"))
                .addLoreLine(Text.get("maingui.feedback.lore.3"))
                .addLoreLine(Text.get("maingui.feedback.lore.4"))
                .addLoreLine(Text.get("maingui.feedback.lore.5"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.feedback.lore.6"))
                .build();

        ItemStack feedback_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Command Feedback")
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.feedback.lore.1"))
                .addLoreLine(Text.get("maingui.feedback.lore.2"))
                .addLoreLine(Text.get("maingui.feedback.lore.3"))
                .addLoreLine(Text.get("maingui.feedback.lore.4"))
                .addLoreLine(Text.get("maingui.feedback.lore.5"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.feedback.lore.6"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
            setItemClickEvent(11, player1 -> feedback_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", false);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(11, player1 -> feedback_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".feedback", true);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        //////////////////////////////////////////

        ItemStack tpa_active = new ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Allow tpa requests")
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.tpa.lore.1"))
                .addLoreLine(Text.get("maingui.tpa.lore.2"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.vanish.lore.4"))
                .build();

        ItemStack tpa_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Allow tpa requests")
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.tpa.lore.1"))
                .addLoreLine(Text.get("maingui.tpa.lore.2"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.vanish.lore.4"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".allowtpas")) {
            setItemClickEvent(13, player1 -> tpa_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".allowtpas", false);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(13, player1 -> tpa_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".allowtpas", true);
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
                .addLoreLine(Text.get("maingui.vanish.lore.1"))
                .addLoreLine(Text.get("maingui.vanish.lore.2"))
                .addLoreLine(Text.get("maingui.vanish.lore.3"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.vanish.lore.4"))
                .build();

        ItemStack vanish_inactive = new ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Fake Join/Quit Message")
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.vanish.lore.1"))
                .addLoreLine(Text.get("maingui.vanish.lore.2"))
                .addLoreLine(Text.get("maingui.vanish.lore.3"))
                .addLoreLine("")
                .addLoreLine(Text.get("maingui.vanish.lore.4"))
                .build();

        if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")) {
            setItemClickEvent(15, player1 -> vanish_active, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", false);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
            });
        } else {
            setItemClickEvent(15, player1 -> vanish_inactive, (player1, event) -> {
                if (event.getClick().isLeftClick()) {
                    Configs.settings.set(player.getUniqueId() + ".vanish" + ".message", true);
                    Configs.saveSettingsConfig();
                    new PlayerSettingsGui(player1).open(player1);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
            });
        }

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="))
                .setName("§fBack").build(), (player1, event) -> {
                    new MainGui(player1).open(player1);
                });
    }

}
