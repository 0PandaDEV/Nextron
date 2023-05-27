package tk.pandadev.nextron.guis.mainextend;

import ch.hekates.languify.language.Text;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import tk.pandadev.nextron.guis.MainGui;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.Utils;

import java.util.Objects;

public class WarpManagerGui extends GUI {

    public WarpManagerGui() {
        super("§7Warp Manager", 5);

        for (String warp : Configs.warp.getConfigurationSection("Warps").getKeys(false)) {
            addItemClickEvent(player1 -> new ItemBuilder(Material.NETHER_STAR)
                    .setName("§f" + warp)
                    .addLoreLine("")
                    .addLoreLine(Text.get("warpgui.leftclick"))
                    .addLoreLine(Text.get("warpgui.rightclick"))
                    .build(), ((player1, event) -> {
                        if (event.getClick().isRightClick()) {
                            player1.teleport((Location) Objects.requireNonNull(Configs.warp.get("Warps." + warp)));
                            player1.playSound(player1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player1.closeInventory();
                        } else if (event.getClick().isLeftClick()) {
                            new WarpSettginsGui(warp).open(player1);
                        }
                    }));
        }

        setItemClickEvent(36, player -> new ItemBuilder(Utils.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="))
                .setName("§fBack")
                .build(), (player, event) -> {
                    new MainGui(player).open(player);
                });
    }

}
