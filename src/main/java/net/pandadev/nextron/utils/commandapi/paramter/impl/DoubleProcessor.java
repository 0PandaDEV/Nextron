package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.command.CommandSender;

public class DoubleProcessor extends Processor<Double> {
    public Double process(CommandSender sender, String supplied) {
        try {
            return Double.parseDouble(supplied);
        } catch (Exception ex) {
            sender.sendMessage(Main.getPrefix() + "§cThe value you entered '" + supplied + "' is an invalid double.");
            return 0D;
        }
    }
}
