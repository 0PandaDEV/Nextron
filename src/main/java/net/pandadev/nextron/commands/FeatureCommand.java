package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.RankAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeatureCommand extends CommandBase implements TabCompleter {

    public FeatureCommand() {
        super("features", "Opens a GUI where you can enable/disable all the systems in the plugin",
                "/features [enable/disable] [feature]", "nextron.features");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        List<String> validValues = Arrays.asList("warp_system", "home_system", "rank_system", "tpa_system", "join_leave_system");

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            Player player = (Player) (sender);
            if (!player.isOp()) {
                player.sendMessage(Main.getNoPerm());
                return;
            }
            GUIs.featureGui(player);
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
        } else if (args.length == 2) {
            if (!validValues.contains(args[1])) {
                sender.sendMessage(Main.getPrefix() + Text.get("feature.validvalues"));
                return;
            }
            if (args[0].equalsIgnoreCase("enable")) {
                Configs.feature.set(args[1], true);
                Configs.saveFeatureConfig();
                for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                    if (args[1].replace("_system", "").equals("tpa")) {
                        onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept",
                                Configs.feature.getBoolean(args[1]));
                    }
                    onlineplayer.addAttachment(Main.getInstance()).setPermission(
                            "nextron." + args[1].replace("_system", ""), Configs.feature.getBoolean(args[1]));
                    if (args[1].replace("_system", "").equalsIgnoreCase("rank")) {
                        RankAPI.checkRank(onlineplayer);
                    }
                }
                if (sender instanceof Player) {
                    Player player = (Player) (sender);
                    (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                }
                sender.sendMessage(
                        Main.getPrefix() + Text.get("feature.enable").replace("%n", args[1].replace("_system", "")));
            }
            if (args[0].equalsIgnoreCase("disable")) {
                Configs.feature.set(args[1], false);
                Configs.saveFeatureConfig();
                for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                    if (Objects.equals(args[1].replace("_system", ""), "tpa")) {
                        onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept",
                                Configs.feature.getBoolean(args[1]));
                    }
                    onlineplayer.addAttachment(Main.getInstance()).setPermission(
                            "nextron." + args[1].replace("_system", ""), Configs.feature.getBoolean(args[1]));
                    if (args[1].replace("_system", "").equalsIgnoreCase("rank")) {
                        RankAPI.checkRank(onlineplayer);
                    }
                }
                if (sender instanceof Player) {
                    Player player = (Player) (sender);
                    (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
                }
                sender.sendMessage(
                        Main.getPrefix() + Text.get("feature.disable").replace("%n", args[1].replace("_system", "")));
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/features [enable/disable] [feature]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            list.add("enable");
            list.add("disable");
        } else if (args.length == 2) {
            list.add("warp_system");
            list.add("home_system");
            list.add("rank_system");
            list.add("tpa_system");
            list.add("join_leave_system");
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }

}