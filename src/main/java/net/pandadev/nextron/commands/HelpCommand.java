package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.arguments.objects.Help;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

@Command(name = "nextron", aliases = {"nhelp"})
@Permission("nextron.help")
public class HelpCommand extends HelpBase {

    public HelpCommand() {
        super("help, Gives you a list of all commands with a short description, /nhelp [command]\n/nextron help [command]");
    }

    @Execute
    public void helpCommand(@Context CommandSender sender, @OptionalArg Help commandName) {
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

    private void displayCommandDetails(CommandSender sender, Help commandInfo) {
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