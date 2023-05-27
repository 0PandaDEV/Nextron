package tk.pandadev.nextron.guis.mainextend;

import ch.hekates.languify.language.Text;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.guis.MainGui;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.Utils;

import java.util.Objects;

public class HomeManagerGui extends GUI {

    public HomeManagerGui(Player player) {
        super("§7Home Manager", 5);

        for (String home : Configs.home.getConfigurationSection("Homes." + player.getUniqueId()).getKeys(false)) {
            addItemClickEvent(player1 -> new ItemBuilder(Utils.createSkull(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0="))
                    .setName(home)
                    .addLoreLine("")
                    .addLoreLine(Text.get("homegui.leftclick"))
                    .addLoreLine(Text.get("homegui.rightclick"))
                    .build(), ((player1, event) -> {
                        if (event.getClick().isRightClick()) {
                            player.teleport((Location) Objects
                                    .requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + "." + home)));
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player.closeInventory();
                        } else if (event.getClick().isLeftClick()) {
                            new HomeSettingsGui(home).open(player1);
                        }
                    }));
        }

        setItemClickEvent(36, player1 -> new ItemBuilder(Utils.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="))
                .setName("§fBack")
                .build(), (player1, event) -> {
                    new MainGui(player1).open(player1);
                });
    }

}
