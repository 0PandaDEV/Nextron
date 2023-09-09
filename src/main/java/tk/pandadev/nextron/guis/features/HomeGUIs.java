package tk.pandadev.nextron.guis.features;

import ch.hekates.languify.language.Text;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.guis.GUIs;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.Utils;

import java.util.Collections;
import java.util.Objects;

public class HomeGUIs {

    public static void manager(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Home Manager"))
                .rows(5)
                .disableAllInteractions()
                .create();

        for (String home : Configs.home.getConfigurationSection("Homes." + player.getUniqueId()).getKeys(false)) {
            gui.addItem(ItemBuilder.from(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0="))
                    .name(Component.text(home))
                    .setLore("",
                            Text.get("homegui.leftclick"),
                            Text.get("homegui.rightclick"))
                    .asGuiItem(inventoryClickEvent -> {
                if (inventoryClickEvent.getClick().isRightClick()) {
                    player.teleport((Location) Objects
                            .requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + "." + home)));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    player.closeInventory();
                } else if (inventoryClickEvent.getClick().isLeftClick()) {
                    settings(player, home);
                }
            }));

        }

        gui.setItem(5, 1, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            GUIs.mainGui(player);
        }));

        gui.open(player);
    }

    public static void settings(Player player, String home){
        Gui gui = Gui.gui()
                .title(Component.text("§7" + home))
                .rows(3)
                .disableAllInteractions()
                .create();

        gui.setItem(2, 4, ItemBuilder.from(Material.ENDER_PEARL)
                .name(Component.text("§x§0§1§5§9§5§6Teleport"))
                .asGuiItem(inventoryClickEvent -> {
                    player.teleport((Location) Objects
                            .requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + "." + home)));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    player.closeInventory();
                }));

        gui.setItem(2, 5, ItemBuilder.from(Material.YELLOW_DYE)
                .name(Component.text("§eRename"))
                .asGuiItem(inventoryClickEvent -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                Location location = (Location) Configs.home
                                        .get("Homes." + player.getUniqueId() + "." + home);
                                Configs.home.set("Homes." + player.getUniqueId() + "." + text.getText(), location);
                                Configs.home.set("Homes." + player.getUniqueId() + "." + home, null);
                                Configs.saveHomeConfig();
                                player.sendMessage(Main.getPrefix() + Text.get("home.rename.success").replace("%h", home)
                                        .replace("%n", text.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(home)
                            .itemLeft(new tk.pandadev.nextron.utils.ItemBuilder(Material.NAME_TAG).setName("§x§e§6§c§7§8§c" + home).build())
                            .title("Type the new Name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(2, 6, ItemBuilder.from(Material.RED_DYE).name(Component.text("§cDelete")).asGuiItem(inventoryClickEvent -> {
            Configs.home.set("Homes." + player.getUniqueId() + "." + home, null);
            Main.getInstance().saveConfig();
            if (Configs.home.getConfigurationSection("Homes." + player.getUniqueId()).getKeys(false)
                    .isEmpty()) {
                player.closeInventory();
            } else {
                manager(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        gui.setItem(3, 1, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            GUIs.mainGui(player);
        }));

        gui.open(player);
    }

}
