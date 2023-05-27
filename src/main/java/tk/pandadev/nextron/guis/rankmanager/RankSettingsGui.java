package tk.pandadev.nextron.guis.rankmanager;

import ch.hekates.languify.language.Text;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.Utils;

import java.util.Collections;

public class RankSettingsGui extends GUI {

    private static final FileConfiguration mainConfig = Main.getInstance().getConfig();

    public RankSettingsGui(String rank) {
        super("§7" + rank, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ARROW).setName("§fChange Prefix").build(),
                ((player, event) -> {
                    new AnvilGUI.Builder()
                            .onComplete((completion) -> {
                                RankAPI.setPrefix(player, rank.toLowerCase(),
                                        ChatColor.translateAlternateColorCodes('&', " " + completion.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(Main.getInstance().getConfig().getString("Ranks." + rank.toLowerCase() + ".prefix"))
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the prefix")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        setItemClickEvent(13, player -> new ItemBuilder(Material.YELLOW_DYE).setName("§eRename").build(),
                ((player, event) -> {
                    new AnvilGUI.Builder()
                            .onComplete((completion) -> {
                                if (Utils.countWords(completion.getText()) > 1) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                                    return Collections.singletonList(AnvilGUI.ResponseAction
                                            .replaceInputText(Text.get("anvil.gui.one.word")));
                                }
                                RankAPI.rename(player, rank.toLowerCase(),
                                        ChatColor.translateAlternateColorCodes('&', " " + completion.getText()));
                                return Collections.singletonList(AnvilGUI.ResponseAction.close());
                            })
                            .text(rank.toLowerCase())
                            .itemLeft(new ItemStack(Material.NAME_TAG))
                            .title("Enter the name")
                            .plugin(Main.getInstance())
                            .open(player);
                }));

        setItemClickEvent(14, player -> new ItemBuilder(Material.RED_DYE).setName("§cDelete").build(),
                ((player, event) -> {
                    RankAPI.deleteRank(player, rank);
                    if (mainConfig.getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
                        player.closeInventory();
                    } else {
                        new RankManagerGui().open(player);
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="))
                .setName("§fBack").build(), (player1, event) -> {
                    new RankManagerGui().open(player1);
                });
    }

}
