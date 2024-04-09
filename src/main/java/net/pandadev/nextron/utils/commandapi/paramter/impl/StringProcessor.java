package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.command.CommandSender;

public class StringProcessor extends Processor<String> {

    public String process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            return null;
        }

        return supplied;
    }

}
