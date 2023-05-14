package tk.pandadev.nextron.guis.rankcreate;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.Utils;

import java.util.Collections;

public class ManualRankGui extends GUI {

    public boolean ready;

    public ManualRankGui(Player player, String name, String prefix){
        super("Manual Rank Creation", 5);

        ItemStack create_off = new ItemBuilder(Material.GRAY_DYE).setName(LanguageLoader.translationMap.get("rank_gui_not_ready")).build();

        ItemStack create_on = new ItemBuilder(Material.LIME_DYE).setName(LanguageLoader.translationMap.get("rank_gui_ready")).build();

        ready = !name.equals("not set") && !prefix.equals("not set");

        setItemClickEvent(22, player1 -> ready ? create_on : create_off, (player1, event) -> {
            if (ready){
                RankAPI.createRank(player, name, ChatColor.translateAlternateColorCodes('&', " " + prefix));
                player.closeInventory();
            }
        });

        setItemClickEvent(20, player1 -> new ItemBuilder(Material.NAME_TAG)
                .setName("§7Name: §a" + name)
                .build(), (player1, event) -> {
            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        if(Utils.countWords(completion.getText()) > 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                            return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(LanguageLoader.translationMap.get("anvil_gui_one_word")));
                        }
                        new ManualRankGui(player, completion.getText().replace(" ", ""), "not set").open(player);
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the name")
                    .plugin(Main.getInstance())
                    .open(player);
        });

        setItemClickEvent(24, player1 -> new ItemBuilder(Material.NAME_TAG)
                .setName("§7Prefix: §a" + prefix)
                .build(), (player1, event) -> {
            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        new ManualRankGui(player, name, completion.getText()).open(player);
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the prefix")
                    .plugin(Main.getInstance())
                    .open(player);
        });

        setItemClickEvent(36, player1 -> new ItemBuilder(Material.RED_DYE).setName("§cQuit").build(), (player1, event) -> {
            player1.closeInventory();
        });
    }
}
