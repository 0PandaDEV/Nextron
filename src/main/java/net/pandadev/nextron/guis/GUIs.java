package net.pandadev.nextron.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.*;
import net.pandadev.nextron.guis.features.HomeGUIs;
import net.pandadev.nextron.guis.features.RankGUIs;
import net.pandadev.nextron.guis.features.WarpGUIs;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GUIs {

    public static void mainGui(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Menu"))
                .disableAllInteractions()
                .rows(5)
                .create();

        gui.setItem(3, 3, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0=").name(Component.text("§fSettings")).asGuiItem(inventoryClickEvent -> {
            playerSettings(player);
        }));

        gui.setItem(2, 5, ItemBuilder.from(Material.COMPASS).name(Component.text("§7Home Manager")).asGuiItem(inventoryClickEvent -> {
            if (!FeatureAPI.getFeature("home_system")) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.disabled.homes"));
                return;
            }
            List<String> homes = HomeAPI.getHomes(player);
            if (homes.isEmpty()) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.no.homes"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            } else {
                HomeGUIs.manager(player);
            }
        }));

        gui.setItem(4, 5, ItemBuilder.from(Material.RECOVERY_COMPASS).name(Component.text("§x§2§9§d§f§e§bWarp Manager")).asGuiItem(inventoryClickEvent -> {
            if (!FeatureAPI.getFeature("warp_system")) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.disabled.warps"));
                return;
            }
            List<String> warps = WarpAPI.getWarps();
            if (warps.isEmpty()) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.no.warps"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            } else {
                WarpGUIs.manager(player);
            }
        }));

        if (player.hasPermission("nextron.rank")) {
            gui.setItem(3, 7, ItemBuilder.from(Material.NAME_TAG).name(Component.text("§x§2§9§d§f§e§bRank Manager")).asGuiItem(inventoryClickEvent -> {
                if (!FeatureAPI.getFeature("rank_system")) {
                    player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.disabled.ranks"));
                    return;
                }
                if (RankAPI.getRanks().isEmpty()) {
                    player.sendMessage(Main.getPrefix() + TextAPI.get("maingui.no.ranks"));
                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                } else {
                    RankGUIs.manager(player);
                }
            }));
        } else {
            gui.setItem(3, 7, ItemBuilder.from(Material.BARRIER).name(Component.text(TextAPI.get("no.perms"))).asGuiItem());
        }

        if (player.hasPermission("nextron.features")) {
            gui.setItem(5, 1, ItemBuilder.from(Material.REDSTONE).name(Component.text("§4Feature Manager")).asGuiItem(inventoryClickEvent -> {
                featureGui(player);
            }));
        } else {
            gui.setItem(5, 1, ItemBuilder.from(Material.BARRIER).name(Component.text(TextAPI.get("no.perms"))).asGuiItem());
        }

        gui.open(player);
    }

    public static void playerSettings(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Menu"))
                .disableAllInteractions()
                .rows(3)
                .create();

        ItemStack feedback_active = new net.pandadev.nextron.utils.ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Command feedback")
                .setLore("",
                        TextAPI.get("maingui.feedback.lore.1"),
                        TextAPI.get("maingui.feedback.lore.2"),
                        TextAPI.get("maingui.feedback.lore.3"),
                        TextAPI.get("maingui.feedback.lore.4"),
                        TextAPI.get("maingui.feedback.lore.5"),
                        "",
                        TextAPI.get("maingui.feedback.lore.6"))
                .build();

        ItemStack feedback_inactive = new net.pandadev.nextron.utils.ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Command Feedback")
                .setLore("",
                        TextAPI.get("maingui.feedback.lore.1"),
                        TextAPI.get("maingui.feedback.lore.2"),
                        TextAPI.get("maingui.feedback.lore.3"),
                        TextAPI.get("maingui.feedback.lore.4"),
                        TextAPI.get("maingui.feedback.lore.5"),
                        "",
                        TextAPI.get("maingui.feedback.lore.6"))
                .build();

        if (SettingsAPI.allowsFeedback(player)) {
            gui.setItem(2, 3, ItemBuilder.from(feedback_active).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setFeedback(player, false);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        } else {
            gui.setItem(2, 3, ItemBuilder.from(feedback_inactive).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setFeedback(player, true);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        }

        //////////////////////////////////////

        ItemStack tpa_active = new net.pandadev.nextron.utils.ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Allow tpa requests")
                .setLore("",
                        TextAPI.get("maingui.tpa.lore.1"),
                        TextAPI.get("maingui.tpa.lore.2"),
                        "",
                        TextAPI.get("maingui.tpa.lore.3"))
                .build();

        ItemStack tpa_inactive = new net.pandadev.nextron.utils.ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Allow tpa requests")
                .setLore("",
                        TextAPI.get("maingui.tpa.lore.1"),
                        TextAPI.get("maingui.tpa.lore.2"),
                        "",
                        TextAPI.get("maingui.tpa.lore.3"))
                .build();

        if (!SettingsAPI.allowsTPAs(player)) {
            gui.setItem(2, 5, ItemBuilder.from(tpa_active).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setTPAs(player, false);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        } else {
            gui.setItem(2, 5, ItemBuilder.from(tpa_inactive).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setTPAs(player, true);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        }

        //////////////////////////////////////

        ItemStack vanish_active = new net.pandadev.nextron.utils.ItemBuilder(Material.LIME_DYE)
                .setName("§a✔ §8• §7Fake Join/Quit Message")
                .setLore("",
                        TextAPI.get("maingui.vanish.lore.1"),
                        TextAPI.get("maingui.vanish.lore.2"),
                        TextAPI.get("maingui.vanish.lore.3"),
                        "",
                        TextAPI.get("maingui.vanish.lore.4"))
                .build();

        ItemStack vanish_inactive = new net.pandadev.nextron.utils.ItemBuilder(Material.GRAY_DYE)
                .setName("§c❌ §8• §7Fake Join/Quit Message")
                .setLore("",
                        TextAPI.get("maingui.vanish.lore.1"),
                        TextAPI.get("maingui.vanish.lore.2"),
                        TextAPI.get("maingui.vanish.lore.3"),
                        "",
                        TextAPI.get("maingui.vanish.lore.4"))
                .build();

        if (SettingsAPI.allowsVanishMessage(player)) {
            gui.setItem(2, 7, ItemBuilder.from(vanish_active).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setVanishMessage(player, false);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        } else {
            gui.setItem(2, 7, ItemBuilder.from(vanish_inactive).asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isLeftClick()) {
                    SettingsAPI.setVanishMessage(player, true);
                    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                    playerSettings(player);
                }
            }));
        }

        gui.setItem(3, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            mainGui(player);
        }));

        gui.open(player);
    }

    public static void featureGui(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Menu"))
                .disableAllInteractions()
                .rows(5)
                .create();

        ///////////////////////// Chat Formatting System ////////////////////////////

        ItemStack on_feature = new net.pandadev.nextron.utils.ItemBuilder(Material.STRING)
                .setName("§a✔ §8• §7Chat formatting System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_feature = new net.pandadev.nextron.utils.ItemBuilder(Material.STRING)
                .setName("§c❌ §8• §7Chat formatting System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(2, 3, ItemBuilder.from(FeatureAPI.getFeature("chat_formatting_system") ? on_feature : off_feature).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("chat_formatting_system", !FeatureAPI.getFeature("chat_formatting_system"));

            // rank system activate and deactivate
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                RankAPI.checkRank(onlineplayer);
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }
            player.playSound(player.getLocation(), FeatureAPI.getFeature("chat_formatting_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            featureGui(player);
        }));

        ///////////////////////// Rank System ////////////////////////////

        ItemStack on_rank = new net.pandadev.nextron.utils.ItemBuilder(Material.NAME_TAG)
                .setName("§a✔ §8• §7Rank System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_rank = new net.pandadev.nextron.utils.ItemBuilder(Material.NAME_TAG)
                .setName("§c❌ §8• §7Rank System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(4, 3, ItemBuilder.from(FeatureAPI.getFeature("rank_system") ? on_rank : off_rank).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("rank_system", !FeatureAPI.getFeature("rank_system"));

            // rank system activate and deactivate
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                RankAPI.checkRank(onlineplayer);
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }
            player.playSound(player.getLocation(), FeatureAPI.getFeature("rank_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            featureGui(player);
        }));


        //////////////////////// Warp System ////////////////////////////

        ItemStack on_warp = new net.pandadev.nextron.utils.ItemBuilder(Material.RECOVERY_COMPASS)
                .setName("§a✔ §8• §7Warp System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_warp = new net.pandadev.nextron.utils.ItemBuilder(Material.RECOVERY_COMPASS)
                .setName("§c❌ §8• §7Warp System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(4, 5, ItemBuilder.from(FeatureAPI.getFeature("warp_system") ? on_warp : off_warp).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("warp_system", !FeatureAPI.getFeature("warp_system"));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.warp", FeatureAPI.getFeature("warp_system"));
            }
            player.playSound(player.getLocation(), FeatureAPI.getFeature("warp_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            featureGui(player);
        }));

        ///////////////////////// Home System ////////////////////////////

        ItemStack on_home = new net.pandadev.nextron.utils.ItemBuilder(Material.COMPASS)
                .setName("§a✔ §8• §7Home System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_home = new net.pandadev.nextron.utils.ItemBuilder(Material.COMPASS)
                .setName("§c❌ §8• §7Home System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(4, 7, ItemBuilder.from(FeatureAPI.getFeature("home_system") ? on_home : off_home).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("home_system", !FeatureAPI.getFeature("home_system"));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.home", FeatureAPI.getFeature("home_system"));
            }
            player.playSound(player.getLocation(), FeatureAPI.getFeature("home_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            featureGui(player);
        }));

        ///////////////////////// Tpa System /////////////////////////////

        ItemStack on_tpa = new net.pandadev.nextron.utils.ItemBuilder(Material.BEACON)
                .setName("§a✔ §8• §7Tpa System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_tpa = new net.pandadev.nextron.utils.ItemBuilder(Material.BEACON)
                .setName("§c❌ §8• §7Tpa System")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(2, 5, ItemBuilder.from(FeatureAPI.getFeature("tpa_system") ? on_tpa : off_tpa).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("tpa_system", !FeatureAPI.getFeature("tpa_system"));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpa", FeatureAPI.getFeature("tpa_system"));
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept", FeatureAPI.getFeature("tpa_system"));
            }
            player.playSound(player.getLocation(), FeatureAPI.getFeature("tpa_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            featureGui(player);
        }));

        //////////////////////////////////////////////////////////////////

        /////////////////////// Join/Leave Message ///////////////////////

        ItemStack on_join_leave = new net.pandadev.nextron.utils.ItemBuilder(Material.BELL)
                .setName("§a✔ §8• §7Join/Leave Messages")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        ItemStack off_join_leave = new net.pandadev.nextron.utils.ItemBuilder(Material.BELL)
                .setName("§c❌ §8• §7Join/Leave Messages")
                .setLore("",
                        TextAPI.get("leftclick"))
                .build();

        gui.setItem(2, 7, ItemBuilder.from(FeatureAPI.getFeature("join_leave_system") ? on_join_leave : off_join_leave).asGuiItem(inventoryClickEvent -> {
            FeatureAPI.setFeature("join_leave_system", !FeatureAPI.getFeature("join_leave_system"));
            player.playSound(player.getLocation(),
                    FeatureAPI.getFeature("join_leave_system")
                            ? Sound.BLOCK_BEACON_ACTIVATE
                            : Sound.BLOCK_BEACON_DEACTIVATE,
                    100, 1);
            featureGui(player);
        }));

        //////////////////////////////////////////////////////////////////

        gui.setItem(5, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            mainGui(player);
        }));

        gui.open(player);
    }

}
