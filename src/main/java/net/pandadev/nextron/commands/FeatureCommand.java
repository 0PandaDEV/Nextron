package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.RankAPI;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.Feature;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeatureCommand extends HelpBase {

    public FeatureCommand() {
        super("features", "Opens a GUI where you can enable/disable all the systems in the plugin",
                "/features [enable/disable] [feature]");
    }

    @Command(names = {"features"}, permission = "nextron.features", playerOnly = true)
    public void featuresCommand(Player player) {
        if (!player.isOp()) {
            player.sendMessage(Main.getNoPerm());
            return;
        }
        GUIs.featureGui(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
    }

    @Command(names = {"features enable"}, permission = "nextron.features.enable")
    public void enableCommand(CommandSender sender, @Param(name = "system") Feature feature) {
        List<String> validValues = Arrays.asList("warp_system", "home_system", "rank_system", "tpa_system", "join_leave_system");

        if (!validValues.contains(feature.getName())) {
            sender.sendMessage(Main.getPrefix() + Text.get("feature.validvalues"));
            return;
        }

        Configs.feature.set(feature.getName(), true);
        Configs.saveFeatureConfig();
        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
            if (feature.getName().replace("_system", "").equals("tpa")) {
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept",
                        Configs.feature.getBoolean(feature.getName()));
            }
            onlineplayer.addAttachment(Main.getInstance()).setPermission(
                    "nextron." + feature.getName().replace("_system", ""), Configs.feature.getBoolean(feature.getName()));
            if (feature.getName().replace("_system", "").equalsIgnoreCase("rank")) {
                RankAPI.checkRank(onlineplayer);
            }
        }
        if (sender instanceof Player) {
            Player player = (Player) (sender);
            (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
        }
        sender.sendMessage(
                Main.getPrefix() + Text.get("feature.enable").replace("%n", feature.getName().replace("_system", "")));
    }

    @Command(names = {"features disable"}, permission = "nextron.features.disable")
    public void disableCommand(CommandSender sender, @Param(name = "system") Feature feature) {
        List<String> validValues = Arrays.asList("warp_system", "home_system", "rank_system", "tpa_system", "join_leave_system");

        if (!validValues.contains(feature.getName())) {
            sender.sendMessage(Main.getPrefix() + Text.get("feature.validvalues"));
            return;
        }

        Configs.feature.set(feature.getName(), false);
        Configs.saveFeatureConfig();
        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
            if (Objects.equals(feature.getName().replace("_system", ""), "tpa")) {
                onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept",
                        Configs.feature.getBoolean(feature.getName()));
            }
            onlineplayer.addAttachment(Main.getInstance()).setPermission(
                    "nextron." + feature.getName().replace("_system", ""), Configs.feature.getBoolean(feature.getName()));
            if (feature.getName().replace("_system", "").equalsIgnoreCase("rank")) {
                RankAPI.checkRank(onlineplayer);
            }
        }
        if (sender instanceof Player) {
            Player player = (Player) (sender);
            (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
        }
        sender.sendMessage(
                Main.getPrefix() + Text.get("feature.disable").replace("%n", feature.getName().replace("_system", "")));
    }

}