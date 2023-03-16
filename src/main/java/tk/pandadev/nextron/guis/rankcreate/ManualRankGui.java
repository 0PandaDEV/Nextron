package tk.pandadev.nextron.guis.rankcreate;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Utils;

import java.util.Arrays;

public class ManualRankGui extends GUI {

    public ManualRankGui(Player player, String name, String prefix){
        super("Manual Rank Creation", 5);

        new AnvilGUI.Builder()
                .onComplete((completion) -> {
                    if(Utils.countWords(completion.getText()) > 1) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("Only one word"));
                    }

                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .preventClose()
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("Enter the name")
                .plugin(Main.getInstance())
                .open(player);

        setItemClickEvent(36, player1 -> new ItemBuilder(Material.RED_DYE).setName("§cQuit").build(), (player1, event) -> {
            player1.closeInventory();
        });
    }

}
