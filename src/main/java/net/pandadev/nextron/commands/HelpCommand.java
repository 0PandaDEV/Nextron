package net.pandadev.nextron.commands;

import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.HelpCommandInfo;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class HelpCommand extends HelpBase {

    public HelpCommand() {
        super("help, Gives you a list of all commands with a short description, /help [command]");
    }

    @Command(names = {"help"}, permission = "nextron.help")
    public void helpCommand(CommandSender sender,
                            @Param(name = "command", required = false) HelpCommandInfo commandName) {
        if (commandName == null) {
            displayAllCommands(sender);
        } else {
            displayCommandDetails(sender, commandName);
        }
    }

    private void displayAllCommands(CommandSender sender) {
        ArrayList<String> commandsList = new ArrayList<>(HelpBase.commands.keySet());
        Collections.sort(commandsList);
        sender.sendMessage("");
        sender.sendMessage("§7Help menu for §x§b§1§8§0§f§f§lNextron §7with all commands");
        sender.sendMessage("");
        for (String command : commandsList) {
            sender.sendMessage("§7/" + command);
        }
        sender.sendMessage("");
        sender.sendMessage("§7For details type §a/help <command>");
        sender.sendMessage("");
    }

    private void displayCommandDetails(CommandSender sender, HelpCommandInfo commandInfo) {
        String usage = "\n";

        if (!commandInfo.getUsage().isEmpty()) {
            if (!commandInfo.getUsage().contains("\n")) {
                usage = "§7Usage: §a" + commandInfo.getUsage();
            } else {
                usage = "§7Usage: \n§a" + commandInfo.getUsage();
            }
        }

        sender.sendMessage(
                "§8----- [ §7Help menu for §x§b§1§8§0§f§f§l" + commandInfo.getCommand() + " §8] -----",
                "",
                "§7Command: §a" + commandInfo.getName(),
                "§7Description: §a" + commandInfo.getDescription(),
                usage,
                "",
                "§8§l-------------" + commandInfo.getCommand().replaceAll("[a-z]", "-") + "----------");
    }
}