package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.RankAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RankCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player)(sender);

        if (args[0].equalsIgnoreCase("set") && args.length == 3){

            if (player.hasPermission("essentialsp.rank.set")) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target != null){

                    RankAPI.setRank(target, args[2]);
                    RankAPI.checkRank(target);

                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else if (args[0].equalsIgnoreCase("remove") && args.length == 2){

            if (player.hasPermission("essentialsp.rank.remove")) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target != null){

                    RankAPI.removeRanks(target);
                    player.sendMessage(Main.getPrefix() + "§7Dem Spieler §a" + target.getName() + "§7 wurden alle Ranks entzogen");
                    RankAPI.checkRank(target);

                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }
        } else if (args[0].equalsIgnoreCase("create") && args.length >= 3){
            if (player.hasPermission("essentialsp.rank.create")) {

                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < args.length; i++) {
                    if (i > 1) sb.append(" ");
                    sb.append(args[i]);
                }

                RankAPI.createRank(player, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', String.valueOf(sb)));

            } else {
                player.sendMessage(Main.getNoPerm());
            }
        } else if (args[0].equalsIgnoreCase("delete") && args.length == 2){

            if (player.hasPermission("essentialsp.rank.delete")) {

                RankAPI.deleteRank(player, args[1].toLowerCase());

            }else {
                player.sendMessage(Main.getNoPerm());
            }

        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("prefix") && args.length >= 4){

            if (player.hasPermission("essentialsp.rank.modify.prefix")) {

                StringBuilder sb = new StringBuilder();
                for(int i = 3; i < args.length; i++) {
                    if (i > 1) sb.append(" ");
                    sb.append(args[i]);
                }

                RankAPI.setPrefix(player, args[2].toLowerCase(), ChatColor.translateAlternateColorCodes('&', String.valueOf(sb)));

            }else {
                player.sendMessage(Main.getNoPerm());
            }

        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("name") && args.length >= 4){

            if (player.hasPermission("essentialsp.rank.modify.name")) {

                StringBuilder sb = new StringBuilder();
                for(int i = 3; i < args.length; i++) {
                    if (i > 1) sb.append(" ");
                    sb.append(args[i]);
                }

                RankAPI.setName(player, args[2].toLowerCase(), String.valueOf(sb));

            } else {
                player.sendMessage(Main.getNoPerm());
            }

        }

        else {
            player.sendMessage(Main.getPrefix() + "§c/setrank|removerank <player> <rankname>");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1) {
            list.add("set");
            list.add("remove");
            list.add("create");
            list.add("delete");
            list.add("modify");
        } else if (args[0].equalsIgnoreCase("set") && args.length == 3){
            list.addAll(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("ranks")).getKeys(false));
        } else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove") && args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        } else if (args[0].equalsIgnoreCase("delete") && args.length == 2){
            list.addAll(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("ranks")).getKeys(false));
        } else if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            list.add("<rankname>");
        } else if (args[0].equalsIgnoreCase("create") && args.length == 3) {
            list.add("<prefix>");
        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("prefix") && args.length == 4) {
            list.add("<prefix>");
        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("prefix") && args.length == 3) {
            list.addAll(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("ranks")).getKeys(false));
        } else if (args[0].equalsIgnoreCase("modify") && args.length == 2){
            list.add("prefix");
            list.add("name");
        } else if (args[0].equalsIgnoreCase("modify") && args.length == 3){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("name") && args.length == 3) {
            list.addAll(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("ranks")).getKeys(false));
        } else if (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("name") && args.length == 4){
            list.add("<rankname>");
        }

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