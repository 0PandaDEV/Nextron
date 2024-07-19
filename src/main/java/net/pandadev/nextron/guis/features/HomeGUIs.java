package net.pandadev.nextron.guis.features;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.HomeAPI;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.languages.TextAPI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HomeGUIs {

    public static void manager(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Home Manager"))
                .rows(5)
                .disableAllInteractions()
                .create();

        List<String> homes = HomeAPI.getHomes(player);
        for (String home : homes) {
            gui.addItem(ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
                    .name(Component.text("§f" + home))
                    .setLore("",
                            TextAPI.get("homegui.leftclick"),
                            TextAPI.get("homegui.rightclick"))
                    .asGuiItem(inventoryClickEvent -> {
                        if (inventoryClickEvent.getClick().isRightClick()) {
                            Location location = HomeAPI.getHome(player, home);
                            if (location != null) {
                                player.teleport(location);
                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                                player.closeInventory();
                            }
                        } else if (inventoryClickEvent.getClick().isLeftClick()) {
                            settings(player, home);
                        }
                    }));
        }

        gui.setItem(5, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            GUIs.mainGui(player);
        }));

        gui.open(player);
    }

    public static void settings(Player player, String home) {
        Gui gui = Gui.gui()
                .title(Component.text("§7" + home))
                .rows(3)
                .disableAllInteractions()
                .create();

        gui.setItem(2, 4, ItemBuilder.from(Material.ENDER_PEARL)
                .name(Component.text("§3Teleport"))
                .asGuiItem(inventoryClickEvent -> {
                    Location location = HomeAPI.getHome(player, home);
                    if (location != null) {
                        player.teleport(location);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                        player.closeInventory();
                    }
                }));

        gui.setItem(2, 5, ItemBuilder.from(Material.YELLOW_DYE)
                .name(Component.text("§eRename"))
                .asGuiItem(inventoryClickEvent -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                HomeAPI.renameHome(player, home, text.getText());
                                player.sendMessage(Main.getPrefix() + TextAPI.get("home.rename.success").replace("%h", home)
                                        .replace("%n", text.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(home)
                            .itemLeft(new net.pandadev.nextron.utils.ItemBuilder(Material.NAME_TAG).setName("§x§e§6§c§7§8§c" + home).build())
                            .title("Type the new Name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(2, 6, ItemBuilder.from(Material.RED_DYE).name(Component.text("§cDelete")).asGuiItem(inventoryClickEvent -> {
            HomeAPI.deleteHome(player, home);
            if (HomeAPI.getHomes(player).isEmpty()) {
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
