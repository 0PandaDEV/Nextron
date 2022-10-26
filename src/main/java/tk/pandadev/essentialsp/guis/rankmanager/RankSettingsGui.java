package tk.pandadev.essentialsp.guis.rankmanager;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.listeners.InputListener;
import tk.pandadev.essentialsp.utils.Utils;

public class RankSettingsGui extends GUI {

    public RankSettingsGui(String rank) {
        super(rank, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ARROW).setName("Rename").build(), ((player, event) -> {
            player.sendMessage("clicked");
            InputListener.listen(player.getUniqueId(), new InputListener.InputListenerResponse() {
                @Override
                public void handle(AsyncPlayerChatEvent event) {
                    player.sendMessage("sdfgh");
                }
            });
        }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new RankManagerGui().open(player1);
        });
    }

}
