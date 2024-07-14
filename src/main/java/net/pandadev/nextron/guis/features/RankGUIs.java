package net.pandadev.nextron.guis.features;

import ch.hekates.languify.language.Text;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class RankGUIs {

    public static void manager(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("Rank Manager"))
                .rows(5)
                .disableAllInteractions()
                .create();

        for (String rank : RankAPI.getRanks()) {
            gui.addItem(ItemBuilder.from(Material.NAME_TAG)
                    .name(Component.text("§f" + rank))
                    .setLore("",
                            "§8Prefix: " + RankAPI.getRankPrefix(rank) + "§8<player>")
                    .asGuiItem(inventoryClickEvent -> {
                        settings(player, rank);
                    }));
        }

        gui.setItem(5, 1, ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").name(Component.text("§fBack")).asGuiItem(inventoryClickEvent -> {
            GUIs.mainGui(player);
        }));

        gui.open(player);
    }


    public static void settings(Player player, String rank) {
        Gui gui = Gui.gui()
                .title(Component.text(rank))
                .rows(3)
                .disableAllInteractions()
                .create();

        gui.setItem(2, 4, ItemBuilder.from(Material.ARROW)
                .name(Component.text("§fChange Prefix"))
                .asGuiItem(inventoryClickEvent -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                RankAPI.setPrefix(player, rank.toLowerCase(),
                                        ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(RankAPI.getRankPrefix(rank.toLowerCase()).replace("§", "&"))
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the prefix")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(2, 5, ItemBuilder.from(Material.YELLOW_DYE)
                .name(Component.text("§eRename"))
                .asGuiItem(inventoryClickEvent -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                if (Utils.countWords(text.getText()) > 1) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                                    return Collections.singletonList(AnvilGUI.ResponseAction
                                            .replaceInputText(Text.get("anvil.gui.one.word")));
                                }
                                RankAPI.rename(player, rank.toLowerCase(),
                                        ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(rank.toLowerCase())
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(2, 6, ItemBuilder.from(Material.RED_DYE)
                .name(Component.text("§cDelete"))
                .asGuiItem(inventoryClickEvent -> {
                    RankAPI.deleteRank(player, rank);
                    if (RankAPI.getRanks().isEmpty()) {
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

    ////////////////////////////////////////////

    public static boolean ready;

    public static void templateRanks(Player player) {
        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(5)
                .title(Component.text("Template"))
                .create();

        gui.setItem(3, 3, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.RED_DYE).setName("§cOwner")
                .asGuiItem(event -> {
                    manualRankCreation(player, "001owner", "§4Owner §8• §f");
                }));

        gui.setItem(3, 5, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.ORANGE_DYE)
                .setName("§x§f§e§a§1§3§1Admin").asGuiItem(event -> {
                    manualRankCreation(player, "002admin", "§x§f§e§a§1§3§1Admin §8• §f");
                }));

        gui.setItem(3, 7, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.PURPLE_DYE)
                .setName("§x§c§d§7§4§f§bDev").asGuiItem(event -> {
                    manualRankCreation(player, "003dev", "§x§c§d§7§4§f§bDev §8• §f");
                }));

        gui.setItem(5, 9, dev.triumphteam.gui.builder.item.ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")
                .name(Component.text("§fSkip")).asGuiItem(event -> {
                    manualRankCreation(player, RankAPI.getHighestNumber() + "name", "prefix §8• §f");
                }));

        gui.open(player);
    }

    public static void manualRankCreation(Player player, String name, String prefix) {
        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(5)
                .title(Component.text("Rank Creation"))
                .create();

        ItemStack create_off = new net.pandadev.nextron.utils.ItemBuilder(Material.GRAY_DYE).setName(Text.get("rank.gui.not.ready")).build();

        ItemStack create_on = new net.pandadev.nextron.utils.ItemBuilder(Material.LIME_DYE).setName(Text.get("rank.gui.ready")).build();

        ready = !name.equals("not set") && !prefix.equals("not set");

        gui.setItem(3, 5, ready ? dev.triumphteam.gui.builder.item.ItemBuilder.from(create_on).asGuiItem(event -> {
            if (ready) {
                RankAPI.createRank(player, name, ChatColor.translateAlternateColorCodes('&', " " + prefix));
                player.closeInventory();
            }
        }) : dev.triumphteam.gui.builder.item.ItemBuilder.from(create_off).asGuiItem(event -> {
            if (ready) {
                RankAPI.createRank(player, name, ChatColor.translateAlternateColorCodes('&', " " + prefix));
                player.closeInventory();
            }
        }));

        gui.setItem(3, 3, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.NAME_TAG)
                .name(Component.text("§7Name: §8" + name)).asGuiItem(event -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                if (Utils.countWords(text.getText()) > 1) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                                    return Collections.singletonList(
                                            AnvilGUI.ResponseAction.replaceInputText(Text.get("anvil.gui.one.word")));
                                }
                                manualRankCreation(player, text.getText().replace(" ", ""), prefix);
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(name)
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(3, 7, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.NAME_TAG)
                .name(Component.text("§7Prefix: §8" + prefix)).asGuiItem(event -> {
                    new AnvilGUI.Builder()
                            .onClick((state, text) -> {
                                manualRankCreation(player, name, text.getText());
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(prefix)
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the prefix")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        gui.setItem(5, 1, dev.triumphteam.gui.builder.item.ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")
                .name(Component.text("§fBack")).asGuiItem(event -> {
                    templateRanks(player);
                }));

        gui.open(player);
    }

}
