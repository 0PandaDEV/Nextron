package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpCommand extends CommandBase implements TabCompleter {

    public HelpCommand() {
        super("help", "Gives you a list of all commands with a short description", "/help", "nextron.help");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {


        if (args.length == 0) {
            ArrayList<String> commandsList = new ArrayList<>(CommandBase.commands.keySet());
            Collections.sort(commandsList);
            sender.sendMessage("");
            sender.sendMessage("§7Help menu for §x§b§1§8§0§f§f§lNextron §7with all commands");
            sender.sendMessage("");
            for (String command : commandsList) {
                sender.sendMessage("§7/" + command);
            }
            sender.sendMessage("");
            sender.sendMessage("§7For details type  §a/help <command>");
            sender.sendMessage("");
        } else if (args.length == 1) {
            if (!CommandBase.commands.containsKey(args[0])) {
                sender.sendMessage(Main.getPrefix() + Text.get("help.command.error"));
                return;
            }
            String usage = "\n";

            if (!CommandBase.getUsage(args[0]).isEmpty()) {
                if (!CommandBase.getUsage(args[0]).contains("\n")) {
                    usage = "§7Usage: §a" + CommandBase.getUsage(args[0]);
                } else {
                    usage = "§7Usage: \n§a" + CommandBase.getUsage(args[0]);
                }
            }

            sender.sendMessage(
                    "§8----- [ §7Help menu for §x§b§1§8§0§f§f§l" + args[0] + " §8] -----",
                    "",
                    "§7Command: §a" + CommandBase.getName(args[0]),
                    "§7Description: §a" + CommandBase.getDescription(args[0]),
                    usage,
                    "",
                    "§8§l-------------" + args[0].replaceAll("[a-z]", "-") + "----------"
            );
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/help [command]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            CommandBase.commands.forEach((name, description) -> {
                list.add(name);
            });
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