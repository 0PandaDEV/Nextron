package tk.pandadev.essentialsp.guis.rankmanager;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.utils.Utils;

public class RankManagerGui extends GUI {

    private static FileConfiguration mainConfig = Main.getInstance().getConfig();

    public RankManagerGui() {
        super("§7Rank Manager", 5);

        for (String rank : mainConfig.getConfigurationSection("Ranks").getKeys(false)){
            addItemClickEvent(player1 -> new ItemBuilder(Material.NAME_TAG)
                    .setName("§x§e§6§c§7§8§c" + rank)
                    .addLoreLine("")
                    .addLoreLine("§8Prefix: " + mainConfig.get("Ranks." + rank + ".prefix") + "§8<player>")
                    .build(), ((player1, event) -> {
                        new RankSettingsGui(rank).open(player1);
            }));
        }

        setItemClickEvent(36, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new MainGui(player1).open(player1);
        });
    }

}
