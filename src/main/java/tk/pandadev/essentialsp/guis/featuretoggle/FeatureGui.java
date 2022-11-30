package tk.pandadev.essentialsp.guis.featuretoggle;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.utils.Utils;

public class FeatureGui extends GUI {

    public FeatureGui(){
        super("§7Feature Manager", 5);



        setItemClickEvent(36, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new MainGui(player1).open(player1);
        });
    }

}
