package tk.pandadev.nextron.guis.mainextend;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.listeners.InputListener;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.Utils;

import java.util.Objects;

public class WarpSettginsGui extends GUI {

    public WarpSettginsGui(String warp){
        super("§7" + warp, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ENDER_PEARL).setName("§x§0§1§5§9§5§6Teleport").build(), ((player, event) -> {
            player.teleport((Location) Objects.requireNonNull(Configs.warp.get("Warps." + warp)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.closeInventory();
        }));
        setItemClickEvent(13, player -> new ItemBuilder(Material.YELLOW_DYE).setName("§eRename").build(), ((player, event) -> {
            if (!player.hasPermission("nextron.rename")){
                player.sendMessage(Main.getNoPerm());
                return;
            }
            player.closeInventory();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_rename_request").replace("%w", warp));
            InputListener.listen(player.getUniqueId(), new InputListener.InputListenerResponse() {
                @Override
                public void handle(AsyncPlayerChatEvent event) {
                    event.setCancelled(true);
                    Location location = (Location) Configs.warp.get("Warps." + warp);
                    Configs.warp.set("Warps." + event.getMessage(), location);
                    Configs.warp.set("Warps." + warp, null);
                    Configs.saveWarpConfig();
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_rename_success").replace("%w", warp).replace("%n", event.getMessage()));
                }
            });
        }));
        setItemClickEvent(14, player -> new ItemBuilder(Material.RED_DYE).setName("§cDelete").build(), ((player, event) -> {
            if (!player.hasPermission("nextron.delwarp")){
                player.sendMessage(Main.getNoPerm());
                return;
            }
            Configs.warp.set("Warps." + warp, null);
            Configs.saveWarpConfig();
            if (Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()) {
                player.closeInventory();
            } else {
                new WarpManagerGui().open(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new HomeManagerGui(player1).open(player1);
        });
    }

}
