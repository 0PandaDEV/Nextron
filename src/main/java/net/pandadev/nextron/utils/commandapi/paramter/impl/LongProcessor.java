package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.command.CommandSender;

public class LongProcessor extends Processor<Long> {
    public Long process(CommandSender sender, String supplied) {
        try {
            return Long.parseLong(supplied);
        } catch (Exception ex) {
            sender.sendMessage(Main.getPrefix() + "§cThe value you entered '" + supplied + "' is an invalid long.");
            return 0L;
        }
    }
}
