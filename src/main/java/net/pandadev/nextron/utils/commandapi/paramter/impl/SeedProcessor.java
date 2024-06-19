package net.pandadev.nextron.utils.commandapi.paramter.impl;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Seed;
import org.bukkit.command.CommandSender;

public class SeedProcessor extends Processor<Seed> {

    public Seed process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            return null;
        }

        long seed;
        try {
            seed = Long.parseLong(supplied);
        } catch (NumberFormatException e) {
            sender.sendMessage(Main.getPrefix() + Text.get("world.create.seed.error"));
            return null;
        }

        return new Seed(seed);
    }

}