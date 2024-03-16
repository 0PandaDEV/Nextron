package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.features.RankGUIs;
import net.pandadev.nextron.utils.RankAPI;
import net.pandadev.nextron.utils.Utils;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public RankCommand() {
        super("rank", "Allows you to create ranks with prefixes to group players", "/rank <player> <rank>", "nextron.rank");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) (sender);
            RankAPI.createPlayerTeam(player);
            RankAPI.checkRank(player);
        }

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);
            if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null || Main
                    .getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
                player.sendMessage(Main.getPrefix() + Text.get("maingui.no.ranks"));
                player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                return;
            }

            RankGUIs.manager(((Player) (sender)).getPlayer());

        } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {

            if (!sender.hasPermission("nextron.rank.set")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Main.getInvalidPlayer());
                return;
            }
            RankAPI.setRank(sender, target, args[2]);
            RankAPI.checkRank(target);

        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {

            if (!sender.hasPermission("nextron.rank.delete")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            RankAPI.deleteRank((Player) sender, args[1].toLowerCase());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {

            if (!sender.hasPermission("nextron.rank.remove")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Main.getInvalidPlayer());
                return;
            }

            RankAPI.removeRanks(target);
            RankAPI.checkRank(target);

            sender.sendMessage(Main.getPrefix()
                    + Text.get("rank.remove.success").replace("%t", target.getName()));

        } else if (args.length == 1 && args[0].equalsIgnoreCase("create")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            RankGUIs.templateRanks(player);

        } else if (args.length == 2 && args[0].equalsIgnoreCase("prefix")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!sender.hasPermission("nextron.rank.prefix")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            new AnvilGUI.Builder()
                    .onClick((state, text) -> {
                        RankAPI.setPrefix((Player) sender, args[1].toLowerCase(),
                                ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .text(Main.getInstance().getConfig().getString("Ranks." + args[1].toLowerCase() + ".prefix").replace("§", "&"))
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the new prefix")
                    .plugin(Main.getInstance())
                    .open(player);

        } else if (args.length == 2 && args[0].equalsIgnoreCase("name")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!sender.hasPermission("nextron.rank.name")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            new AnvilGUI.Builder()
                    .onClick((state, text) -> {
                        if (Utils.countWords(text.getText()) > 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                            return Collections.singletonList(AnvilGUI.ResponseAction
                                    .replaceInputText(Text.get("anvil.gui.one.word")));
                        }
                        RankAPI.rename((Player) sender, args[1].toLowerCase(),
                                ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .text(args[1].toLowerCase())
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the new name")
                    .plugin(Main.getInstance())
                    .open(player);
        } else {
            sender.sendMessage(Main.getPrefix() +
                            "§c/rank set <player> <rank>",
                    "§c/rank remove <player>",
                    "§c/rank create",
                    "§c/rank delete <rank>",
                    "§c/rank prefix <rank>",
                    "§c/rank name <rank>");
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            list.add("set");
            list.add("remove");
            list.add("create");
            list.add("delete");
            list.add("prefix");
            list.add("name");
        }

        // rank set
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
            if (args.length == 3) {
                if (Main.getInstance().getConfig().getConfigurationSection("Ranks") != null)
                    list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
            }
        }

        // rank remove
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 2) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
        }

        // rank delete
        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length == 2) if (Main.getInstance().getConfig().getConfigurationSection("Ranks") != null)
                list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
        }

        // rank rename
        if (args[0].equalsIgnoreCase("prefix") || args[0].equalsIgnoreCase("name")) {
            if (args.length == 2) if (Main.getInstance().getConfig().getConfigurationSection("Ranks") != null)
                list.addAll(Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false));
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