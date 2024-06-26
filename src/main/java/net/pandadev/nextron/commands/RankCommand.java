package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.features.RankGUIs;
import net.pandadev.nextron.listeners.InputListener;
import net.pandadev.nextron.utils.RankAPI;
import net.pandadev.nextron.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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

            if (Main.getInstance().getConfig().get("Ranks." + args[1].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
                return;
            }

            player.sendMessage(Main.getPrefix() + "§7Type the new prefix for the rank in to the chat");
            TextComponent command = new TextComponent("§a[Current Prefix]");
            command.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Main.getInstance().getConfig().getString("Ranks." + args[1].toLowerCase() + ".prefix").replace("§", "&")));
            command.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to get the current prefix of the rank " + Main.getInstance().getConfig().getString("Ranks." + args[1].toLowerCase() + ".prefix")).create()));

            player.spigot().sendMessage(command);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1f);

            InputListener.listen(player.getUniqueId()).thenAccept(response -> {
                RankAPI.setPrefix((Player) sender, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', " " + response));
            });


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

            if (Main.getInstance().getConfig().get("Ranks." + args[1].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
                return;
            }

            player.sendMessage(Main.getPrefix() + "§7Type the new name for the rank in to the chat");
            TextComponent command = new TextComponent("§a[Current Name]");
            command.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, args[1]));
            command.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to get the current name of the rank " + Main.getInstance().getConfig().getString("Ranks." + args[1].toLowerCase() + ".prefix")).create()));

            player.spigot().sendMessage(command);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1f);

            InputListener.listen(player.getUniqueId()).thenAccept(response -> {
                if (Utils.countWords(response) > 1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                    player.sendMessage(Text.get("anvil.gui.one.word"));
                    return;
                }
                RankAPI.rename((Player) sender, args[1].toLowerCase(), ChatColor.translateAlternateColorCodes('&', " " + response));
            });

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