package tk.pandadev.nextron.guis.rankcreate;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.utils.Utils;

public class ChooseTemplateGui extends GUI {

    public ChooseTemplateGui(){
        super("Template", 5);

        ItemStack owner = new ItemBuilder(Material.RED_DYE).setName("&cOwner").build();

        setItemClickEvent(20, player -> owner, (player, event) -> {

        });

        setItemClickEvent(44, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")).setName("§fSkip").build(), (player1, event) -> {
            new ManualRankGui(player1, "not set", "not set").open(player1);
        });

    }

}
