package tk.pandadev.nextron.commands;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.guis.rankcreate.ChooseTemplateGui;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) (sender);
            RankAPI.createPlayerTeam(player);
            RankAPI.checkRank(player);
        }

        if (args.length == 2 && label.equalsIgnoreCase("rank")) {

            if (!sender.hasPermission("nextron.rank.set")) {sender.sendMessage(Main.getNoPerm()); return false;}
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {sender.sendMessage(Main.getInvalidPlayer()); return false;}
            RankAPI.setRank(sender, target, args[1]);
            RankAPI.checkRank(target);

        } else if (args.length == 1 && label.equalsIgnoreCase("removerank")) {

            if (!sender.hasPermission("nextron.rank.remove")) {sender.sendMessage(Main.getNoPerm()); return false;}

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {sender.sendMessage(Main.getInvalidPlayer()); return false;}

            RankAPI.removeRanks(target);
            RankAPI.checkRank(target);

            sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_remove_success").replace("%t", target.getName()));

        } else if (args.length == 0 && label.equalsIgnoreCase("createrank")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return false;
            }

            Player player = (Player) (sender);

            new ChooseTemplateGui().open(player);

        } else if (args.length == 1 && label.equalsIgnoreCase("deleterank")) {

            if (!sender.hasPermission("nextron.rank.delete")) {sender.sendMessage(Main.getNoPerm()); return false;}

            RankAPI.deleteRank((Player) sender, args[0].toLowerCase());

        } else if (args.length == 2 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("prefix")){

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return false;
            }

            Player player = (Player) (sender);

            if (!sender.hasPermission("nextron.rank.modify.prefix")) {sender.sendMessage(Main.getNoPerm()); return false;}

            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        RankAPI.setPrefix((Player) sender, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', " " + completion.getText()));
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the prefix")
                    .plugin(Main.getInstance())
                    .open(player);

        } else if (args.length == 2 && label.equalsIgnoreCase("modifyrank") && args[0].equalsIgnoreCase("name")){

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return false;
            }

            Player player = (Player) (sender);

            if (!sender.hasPermission("nextron.rank.modify.name")) {sender.sendMessage(Main.getNoPerm()); return false;}

            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        if(Utils.countWords(completion.getText()) > 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                            return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(LanguageLoader.translationMap.get("anvil_gui_one_word")));
                        }
                        RankAPI.rename((Player) sender, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', " " + completion.getText()));
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the name")
                    .plugin(Main.getInstance())
                    .open(player);

        } else{
            sender.sendMessage(Main.getPrefix() +
                    "§c/rank <player> <rank>",
                    "§c/removerank <player>",
                    "§c/createrank",
                    "§c/deleterank <rankname>",
                    "§c/modifyrank prefix <rank>",
                    "§c/modifyrank name <rank>");
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
        // delete rank command
        if (args.length == 1 && label.equalsIgnoreCase("deleterank")) list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
        // modify prefix command
        if (args.length == 1 && label.equalsIgnoreCase("modifyrank")) list.add("prefix");
        if (args.length == 1 && label.equalsIgnoreCase("modifyrank")) list.add("name");
        if (args.length == 2 && label.equalsIgnoreCase("modifyrank")) list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));

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