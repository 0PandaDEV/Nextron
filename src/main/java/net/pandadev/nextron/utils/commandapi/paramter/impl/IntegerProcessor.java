package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.command.CommandSender;

public class IntegerProcessor extends Processor<Integer> {
    public Integer process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(supplied);
        } catch (Exception ex) {
            sender.sendMessage(Main.getPrefix() + "§cThe value you entered '" + supplied + "' is an invalid integer.");
            return 0;
        }
    }
}
