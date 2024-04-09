package net.pandadev.nextron.utils.commandapi.paramter.impl;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Seed;
import org.bukkit.command.CommandSender;

public class SeedProcessor extends Processor<Seed> {

    public Seed process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            // Return a default Seed object to avoid null
            return new Seed(0); // You can choose a different default value if needed
        }

        long seed;
        try {
            seed = Long.parseLong(supplied);
        } catch (NumberFormatException e) {
            sender.sendMessage(Main.getPrefix() + Text.get("world.create.seed.error"));
            return new Seed(0); // Return a default Seed object in case of format error
        }

        return new Seed(seed);
    }

}
