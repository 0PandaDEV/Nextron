package tk.pandadev.essentialsp.guis.featuretoggle;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.guis.MainGui;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.Utils;

public class FeatureGui extends GUI {

    public FeatureGui(){
        super("§7Feature Manager", 5);

        ///////////////////////// Warp System ////////////////////////////

        ItemStack on_warp = new ItemBuilder(Material.RECOVERY_COMPASS)
                .setName("§a✔ §8• §7Warp System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_warp_off"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        ItemStack off_warp = new ItemBuilder(Material.RECOVERY_COMPASS)
                .setName("§c❌ §8• §7Warp System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_warp_on"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        setItemClickEvent(21, player -> Configs.feature.getBoolean("warp_system") ? on_warp : off_warp, (player, event) -> {
            Configs.feature.set("warp_system", !Configs.feature.getBoolean("warp_system"));
            Configs.saveFeatureConfig();
            for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                onlineplayer.addAttachment(Main.getInstance()).setPermission("essentialsp.warp", Configs.feature.getBoolean("warp_system"));
            }
            player.playSound(player.getLocation(), Configs.feature.getBoolean("warp_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            new FeatureGui().open(player);
        });

        //////////////////////////////////////////////////////////////////

        ///////////////////////// Home System ////////////////////////////

        ItemStack on_home = new ItemBuilder(Material.COMPASS)
                .setName("§a✔ §8• §7Home System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_home_off"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        ItemStack off_home = new ItemBuilder(Material.COMPASS)
                .setName("§c❌ §8• §7Home System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_home_on"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        setItemClickEvent(23, player -> Configs.feature.getBoolean("home_system") ? on_home : off_home, (player, event) -> {
            Configs.feature.set("home_system", !Configs.feature.getBoolean("home_system"));
            Configs.saveFeatureConfig();
            for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                onlineplayer.addAttachment(Main.getInstance()).setPermission("essentialsp.home", Configs.feature.getBoolean("home_system"));
            }
            player.playSound(player.getLocation(), Configs.feature.getBoolean("home_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            new FeatureGui().open(player);
        });

        //////////////////////////////////////////////////////////////////

        ///////////////////////// Tpa System /////////////////////////////

        ItemStack on_tpa = new ItemBuilder(Material.NAME_TAG)
                .setName("§a✔ §8• §7Tpa System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_tpa_off"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        ItemStack off_tpa = new ItemBuilder(Material.NAME_TAG)
                .setName("§c❌ §8• §7Tpa System")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("featuregui_tpa_on"))
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        setItemClickEvent(25, player -> Configs.feature.getBoolean("tpa_system") ? on_tpa : off_tpa, (player, event) -> {
            Configs.feature.set("tpa_system", !Configs.feature.getBoolean("tpa_system"));
            Configs.saveFeatureConfig();
            for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                onlineplayer.addAttachment(Main.getInstance()).setPermission("essentialsp.tpa", Configs.feature.getBoolean("tpa_system"));
                onlineplayer.addAttachment(Main.getInstance()).setPermission("essentialsp.tpaccept", Configs.feature.getBoolean("tpa_system"));
            }
            player.playSound(player.getLocation(), Configs.feature.getBoolean("tpa_system") ? Sound.BLOCK_BEACON_ACTIVATE : Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            new FeatureGui().open(player);
        });

        //////////////////////////////////////////////////////////////////

        setItemClickEvent(36, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new MainGui(player1).open(player1);
        });
    }

}
