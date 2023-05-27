package tk.pandadev.nextron.guis;

import ch.hekates.languify.language.Text;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.guis.featuretoggle.FeatureGui;
import tk.pandadev.nextron.guis.mainextend.HomeManagerGui;
import tk.pandadev.nextron.guis.mainextend.PlayerSettingsGui;
import tk.pandadev.nextron.guis.mainextend.WarpManagerGui;
import tk.pandadev.nextron.guis.rankmanager.RankManagerGui;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.Utils;

public class MainGui extends GUI {

    public MainGui(Player player) {
        super("§7Menu", 5);

        setItemClickEvent(20, player1 -> new ItemBuilder(Utils.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0="))
                .setName("§fSettings").build(), ((player1, event) -> {
                    new PlayerSettingsGui(player1).open(player1);
                }));

        setItemClickEvent(13, player1 -> new ItemBuilder(Material.COMPASS).setName("§7Home Manager").build(),
                (player1, event) -> {
                    if (Configs.home.getConfigurationSection("Homes." + player1.getUniqueId()) == null || Configs.home
                            .getConfigurationSection("Homes." + player1.getUniqueId()).getKeys(false).isEmpty()) {
                        player.sendMessage(Main.getPrefix() + Text.get("maingui.no.homes"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                    } else {
                        new HomeManagerGui(player1).open(player1);
                    }
                });

        setItemClickEvent(31,
                player1 -> new ItemBuilder(Material.RECOVERY_COMPASS).setName("§x§2§9§d§f§e§bWarp Manager").build(),
                (player1, event) -> {
                    if (Configs.warp.getConfigurationSection("Warps") == null
                            || Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()) {
                        player.sendMessage(Main.getPrefix() + Text.get("maingui.no.warps"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                    } else {
                        new WarpManagerGui().open(player1);
                    }
                });

        if (player.hasPermission("nextron.rank")) {
            setItemClickEvent(24,
                    player1 -> new ItemBuilder(Material.NAME_TAG).setName("§x§e§6§c§7§8§cRank Manager").build(),
                    ((player1, event) -> {
                        if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null || Main
                                .getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
                            player.sendMessage(Main.getPrefix() + Text.get("maingui.no.ranks"));
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                        } else {
                            new RankManagerGui().open(player1);
                        }
                    }));
        } else {
            setItem(24, player1 -> new ItemBuilder(Material.BARRIER).setName("§c§lNo Permision").build());
        }

        if (player.hasPermission("nextron.features")) {
            setItemClickEvent(36, player1 -> new ItemBuilder(Material.REDSTONE).setName("§4Feature Manager").build(),
                    (player1, event) -> {
                        new FeatureGui().open(player1);
                    });
        } else {
            setItem(36, player1 -> new ItemBuilder(Material.BARRIER).setName("§c§lNo Permision").build());
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
