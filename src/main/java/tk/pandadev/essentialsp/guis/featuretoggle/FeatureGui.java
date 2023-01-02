package tk.pandadev.essentialsp.guis.featuretoggle;

import games.negative.framework.gui.GUI;
import games.negative.framework.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
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
                .addLoreLine("§8The warp system can't be used by normal players now!")
                .addLoreLine("")
                .addLoreLine(LanguageLoader.translationMap.get("leftclick"))
                .build();

        setItemClickEvent(13, player -> Configs.feature.getBoolean("warp_system") ? on_warp : off_warp, (player, event) -> {
            Configs.feature.set("warp_system", !Configs.feature.getBoolean("warp_system"));
            Configs.saveFeatureConfig();
            player.addAttachment(Main.getInstance()).setPermission("essentialsp.warp", Configs.feature.getBoolean("warp_system"));
            player.playSound(player.getLocation(), Configs.feature.getBoolean("warp_system") ? Sound.BLOCK_BEACON_DEACTIVATE : Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
            new FeatureGui().open(player);
        });

        //////////////////////////////////////////////////////////////////

        setItemClickEvent(36, player1 -> new ItemBuilder(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).setName("§fBack").build(), (player1, event) -> {
            new MainGui(player1).open(player1);
        });
    }

}
