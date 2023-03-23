package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.guis.featuretoggle.FeatureGui;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.RankAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeatureCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> validValues = Arrays.asList("warp_system", "home_system", "rank_system", "tpa_system");

        if (args.length == 0){
            if (!(sender instanceof Player)) {sender.sendMessage(Main.getCommandInstance()); return false;}
            Player player = (Player) (sender);
            if (!player.isOp()) {player.sendMessage(Main.getNoPerm()); return false;}
            new FeatureGui().open(player);
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
        } else if (args.length == 2){
            if (!validValues.contains(args[1])) {sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("feature_validvalues")); return false;}
            if (args[0].equalsIgnoreCase("enable")){
                Configs.feature.set(args[1], true);
                Configs.saveFeatureConfig();
                for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                    if (args[1].replace("_system", "").equals("tpa")) {onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept", Configs.feature.getBoolean(args[1]));}
                    onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron." + args[1].replace("_system", ""), Configs.feature.getBoolean(args[1]));
                    if (args[1].replace("_system", "").equalsIgnoreCase("rank")) {
                        RankAPI.checkRank(onlineplayer);
                    }
                }
                if (sender instanceof Player) { Player player = (Player) (sender); (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1); }
                sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("feature_enable").replace("%n", args[1].replace("_system", "")));
            }
            if (args[0].equalsIgnoreCase("disable")){
                Configs.feature.set(args[1], false);
                Configs.saveFeatureConfig();
                for (Player onlineplayer : Bukkit.getOnlinePlayers()){
                    if (Objects.equals(args[1].replace("_system", ""), "tpa")) {onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron.tpaccept", Configs.feature.getBoolean(args[1]));}
                    onlineplayer.addAttachment(Main.getInstance()).setPermission("nextron." + args[1].replace("_system", ""), Configs.feature.getBoolean(args[1]));
                    if (args[1].replace("_system", "").equalsIgnoreCase("rank")) {
                        RankAPI.checkRank(onlineplayer);
                    }
                }
                if (sender instanceof Player) { Player player = (Player) (sender); (player).playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1); }
                sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("feature_disable").replace("%n", args[1].replace("_system", "")));
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/features"); return false;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1){
            list.add("enable");
            list.add("disable");
        } else if (args.length == 2){
            list.add("warp_system");
            list.add("home_system");
            list.add("rank_system");
            list.add("tpa_system");
        }

        return list;
    }

}