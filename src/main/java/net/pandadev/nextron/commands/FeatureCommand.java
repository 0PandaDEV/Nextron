package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Feature;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.RankAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@Command(name = "features")
@Permission("nextron.features")
public class FeatureCommand extends HelpBase {

    public FeatureCommand() {
        super("features, Opens a GUI where you can enable/disable all the systems in the plugin, /features [enable/disable] [feature]");
    }

    @Execute()
    void featuresCommand(@Context Player player) {
        if (!player.isOp()) {
            player.sendMessage(Main.getNoPerm());
            return;
        }
        GUIs.featureGui(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
    }

    @Execute(name = "enable")
    void enableCommand(@Context CommandSender sender, @Arg Feature feature) {
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

    @Execute(name = "disable")
    void disableCommand(@Context CommandSender sender, @Arg Feature feature) {
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