package net.pandadev.nextron.utils.commandapi.paramter.impl;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.commands.HelpBase;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.HelpCommandInfo;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelpProcessor extends Processor<HelpCommandInfo> {

    public HelpCommandInfo process(CommandSender sender, String supplied) {
        if (HelpBase.commands.containsKey(supplied)) {
            return new HelpCommandInfo(supplied, HelpBase.getName(supplied), HelpBase.getDescription(supplied), HelpBase.getUsage(supplied));
        }
        sender.sendMessage(Main.getPrefix() + Text.get("help.command.error"));
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        return new ArrayList<>(HelpBase.commands.keySet()).stream()
                .filter(cmd -> cmd.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}