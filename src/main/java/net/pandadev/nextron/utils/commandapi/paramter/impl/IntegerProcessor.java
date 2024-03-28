package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class IntegerProcessor extends Processor<Integer> {
    public Integer process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(supplied);
        } catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + "The value you entered '" + supplied + "' is an invalid integer.");
            return 0;
        }
    }
}
