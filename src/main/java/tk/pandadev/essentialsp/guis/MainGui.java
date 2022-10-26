package tk.pandadev.essentialsp.guis;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.guis.mainextend.HomeGui;
import tk.pandadev.essentialsp.guis.mainextend.PlayerSettingsGui;
import tk.pandadev.essentialsp.guis.mainextend.WarpGui;
import tk.pandadev.essentialsp.guis.rankmanager.RankManagerGui;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.Utils;

public class MainGui extends GUI {

    public MainGui(Player player) {
        super("Menu", 5);

        setItemClickEvent(20, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0=")).setName("§fSettings").build(), ((player1, event) -> {
            new PlayerSettingsGui(player1).open(player1);
        }));

        setItemClickEvent(13, player1 -> new ItemBuilder(Material.COMPASS).setName("§7Homes").build(), (player1, event) -> {
            if (Configs.home.getConfigurationSection("Homes." + player1.getUniqueId()) == null || Configs.home.getConfigurationSection("Homes." + player1.getUniqueId()).getKeys(false).isEmpty()){
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("maingui_no_homes"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            } else {
                new HomeGui(player1).open(player1);
            }
        });

        setItemClickEvent(31, player1 -> new ItemBuilder(Material.RECOVERY_COMPASS).setName("§7Warps").build(), (player1, event) -> {
            if (Configs.warp.getConfigurationSection("Warps") == null || Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()){
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("maingui_no_warps"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            } else {
                new WarpGui().open(player1);
            }
        });

        setItemClickEvent(24, player1 -> new ItemBuilder(Material.NAME_TAG).setName("§x§e§6§c§7§8§cRank Manager").build(), ((player1, event) -> {
            new RankManagerGui().open(player1);
        }));

        ////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
