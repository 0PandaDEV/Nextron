package tk.pandadev.nextron.guis.rankmanager;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.listeners.InputListener;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.Utils;

public class RankSettingsGui extends GUI {

    private static final FileConfiguration mainConfig = Main.getInstance().getConfig();

    public RankSettingsGui(String rank) {
        super("§7" + rank, 3);

        setItemClickEvent(12, player -> new ItemBuilder(Material.ARROW).setName("§fChange Prefix").build(), ((player, event) -> {
            player.closeInventory();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_setprefix_request"));
            InputListener.listen(player.getUniqueId(), new InputListener.InputListenerResponse() {
                @Override
                public void handle(AsyncPlayerChatEvent event) {
                    event.setCancelled(true);
                    mainConfig.set("Ranks." + rank.toLowerCase() + ".prefix", ChatColor.translateAlternateColorCodes('&', event.getMessage()));
                    Main.getInstance().saveConfig();
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_setprefix_success").replace("%r", rank).replace("%p", ChatColor.translateAlternateColorCodes('&', event.getMessage())));
                    Main.getInstance().getTablistManager().setAllPlayerTeams();
                }
            });
        }));

        setItemClickEvent(13, player -> new ItemBuilder(Material.YELLOW_DYE).setName("§eRename").build(), ((player, event) -> {
            player.closeInventory();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_rename_request").replace("%r", rank));
            InputListener.listen(player.getUniqueId(), new InputListener.InputListenerResponse() {
                @Override
                public void handle(AsyncPlayerChatEvent event) {
                    event.setCancelled(true);
                    mainConfig.set("Ranks." + event.getMessage().toLowerCase() + ".prefix", mainConfig.get("Ranks." + rank.toLowerCase() + ".prefix"));
                    mainConfig.set("Ranks." + event.getMessage().toLowerCase() + ".players", mainConfig.get("Ranks." + rank.toLowerCase() + ".players"));
                    mainConfig.set("Ranks." + rank.toLowerCase(), null);
                    Main.getInstance().saveConfig();
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_rename_success").replace("%r", rank.toLowerCase()).replace("%n", event.getMessage().toLowerCase()));
                }
            });
        }));

        setItemClickEvent(14, player -> new ItemBuilder(Material.RED_DYE).setName("§cDelete").build(), ((player, event) -> {
            RankAPI.deleteRank(player, rank);
            if (mainConfig.getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
                player.closeInventory();
            } else {
                new RankManagerGui().open(player);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }));

        setItemClickEvent(18, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new RankManagerGui().open(player1);
        });
    }

}
