package tk.pandadev.essentialsp.guis.mainextend;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.Utils;

import java.util.Objects;

public class WarpSettginsGui extends GUI {

    public WarpSettginsGui(String warp){
        super(warp, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ENDER_PEARL).setName("§x§0§1§5§9§5§6Teleport").build(), ((player, event) -> {
            player.teleport((Location) Objects.requireNonNull(Configs.warp.get("Warps." + warp)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.closeInventory();
        }));
        setItemClickEvent(13, player -> new ItemBuilder(Material.YELLOW_DYE).setName("§eRename").build(), ((player, event) -> {
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("homesettingsgui_feature"));
            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
        }));
        setItemClickEvent(14, player -> new ItemBuilder(Material.RED_DYE).setName("§cDelete").build(), ((player, event) -> {
            if (!player.isOp()) player.sendMessage(Main.getNoPerm());
            Configs.warp.set("Wraps." + warp, null);
            Main.getInstance().saveConfig();
            if (Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()) {
                player.closeInventory();
            } else {
                new WarpGui().open(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new HomeGui(player1).open(player1);
        });
    }

}
