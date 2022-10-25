package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.RankAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RankCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);
        RankAPI.createPlayerTeam(player);
        RankAPI.checkRank(player);

        if (args.length == 2 && label.equalsIgnoreCase("rank")) {

            if (!player.hasPermission("essentialsp.rank.set")) player.sendMessage(Main.getNoPerm());
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) player.sendMessage(Main.getInvalidPlayer());
            RankAPI.setRank(target, args[1]);
            RankAPI.checkRank(target);

        } else if (args.length == 1 && label.equalsIgnoreCase("removerank")) {

            if (!player.hasPermission("essentialsp.rank.remove")) player.sendMessage(Main.getNoPerm());

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) player.sendMessage(Main.getInvalidPlayer());

            RankAPI.removeRanks(target);
            RankAPI.checkRank(target);

            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_remove_success").replace("%t", target.getName()));

        } else if (args.length >= 2 && label.equalsIgnoreCase("createrank")) {

            if (!player.hasPermission("essentialsp.rank.create")) player.sendMessage(Main.getNoPerm());

            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                if (i > 1) sb.append(" ");
                sb.append(args[i]);
            }

            RankAPI.createRank(player, args[0].toLowerCase(), ChatColor.translateAlternateColorCodes('&', String.valueOf(sb)));

        } else if (args.length == 1 && label.equalsIgnoreCase("deleterank")) {

            if (!player.hasPermission("essentialsp.rank.delete")) player.sendMessage(Main.getNoPerm());

            RankAPI.deleteRank(player, args[0].toLowerCase());

        } else if (args.length >= 3 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("prefix")){

            if (!player.hasPermission("essentialsp.rank.modify.prefix")) player.sendMessage(Main.getNoPerm());

            StringBuilder sb = new StringBuilder();
            for(int i = 2; i < args.length; i++) {
                sb.append(" ");
                sb.append(args[i]);
            }

            RankAPI.setPrefix(player, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', String.valueOf(sb)));

        } else{
            player.sendMessage(Main.getPrefix() +
                    "§c/rank <player> <rank>",
                    "§c/removerank <player>",
                    "§c/createrank <rankname> <prefix>",
                    "§c/deleterank <rankname>",
                    "§c/modifyrank prefix <rank> <newprefix>");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        // rank command
        if (args.length == 1 && label.equalsIgnoreCase("rank")) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
        if (args.length == 2 && label.equalsIgnoreCase("rank")) list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
        // remove rank command
        if (args.length == 1 && label.equalsIgnoreCase("removerank")) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
        // create rank command
        if (args.length == 1 && label.equalsIgnoreCase("createrank")) list.add("<rankname>");
        if (args.length == 2 && label.equalsIgnoreCase("createrank")) list.add("<prefix>");
        // delete rank command
        if (args.length == 1 && label.equalsIgnoreCase("deleterank")) list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
        // modify prefix command
        if (args.length == 1 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("prefix")) list.add("prefix");
        if (args.length == 2 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("prefix")) list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
        if (args.length == 3 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("prefix")) list.add("<prefix>");

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }
        return completerList;
    }
}