package net.pandadev.nextron.guis.features;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.WarpAPI;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.languages.TextAPI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class WarpGUIs {

    public static void manager(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Warp Manager"))
                .rows(5)
                .disableAllInteractions()
                .create();

        List<String> warps = WarpAPI.getWarps();
        for (String warp : warps) {
            gui.addItem(ItemBuilder.from(Material.NETHER_STAR)
                    .name(Component.text("§f" + warp))
                    .lore(Component.empty(), Component.text(TextAPI.get("warpgui.leftclick")), Component.text(TextAPI.get("warpgui.rightclick")))
                    .asGuiItem(inventoryClickEvent -> {
                        if (inventoryClickEvent.getClick().isRightClick()) {
                            Location location = WarpAPI.getWarp(warp);
                            if (location != null) {
                                player.teleport(location);
                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                                player.closeInventory();
                            }
                        } else if (inventoryClickEvent.getClick().isLeftClick()) {
                            settings(player, warp);
                        }
                    }));
        }

        gui.setItem(5, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            GUIs.mainGui(player);
        }));

        gui.open(player);
    }

    public static void settings(Player player, String warp) {
        Gui gui = Gui.gui()
                .title(Component.text(warp))
                .rows(3)
                .disableAllInteractions()
                .create();

        gui.setItem(2, 4, ItemBuilder.from(Material.ENDER_PEARL)
                .name(Component.text("§x§0§1§5§9§5§6Teleport"))
                .asGuiItem(inventoryClickEvent -> {
                    Location location = WarpAPI.getWarp(warp);
                    if (location != null) {
                        player.teleport(location);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                        player.closeInventory();
                    }
                }));

        gui.setItem(2, 5, ItemBuilder.from(Material.YELLOW_DYE)
                .name(Component.text("§eRename"))
                .asGuiItem(inventoryClickEvent -> {
                    if (!player.hasPermission("nextron.rename")) {
                        player.sendMessage(Main.getNoPerm());
                        return;
                    }

                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                Location location = WarpAPI.getWarp(warp);
                                if (location != null) {
                                    WarpAPI.setWarp(text.getText(), location);
                                    WarpAPI.deleteWarp(warp);
                                    player.sendMessage(Main.getPrefix() + TextAPI.get("warp.rename.success").replace("%w", warp)
                                            .replace("%n", text.getText()));
                                }
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(warp)
                            .itemLeft(new net.pandadev.nextron.utils.ItemBuilder(Material.NAME_TAG).setName("§x§e§6§c§7§8§c" + warp).build())
                            .title("Type the new Name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(2, 6, ItemBuilder.from(Material.RED_DYE).name(Component.text("§cDelete")).asGuiItem(inventoryClickEvent -> {
            WarpAPI.deleteWarp(warp);
            if (WarpAPI.getWarps().isEmpty()) {
                player.closeInventory();
            } else {
                manager(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        gui.setItem(3, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            manager(player);
        }));

        gui.open(player);
    }

}
