package net.pandadev.nextron.utils.commandapi.paramter.impl;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class WorldProcessor extends Processor<World> {
    @Override
    public World process(CommandSender sender, String supplied) {
        if (supplied == null || supplied.isEmpty()) {
            return null;
        }

        World world = Bukkit.getWorld(supplied);

        if (world == null) {
            sender.sendMessage(Main.getPrefix() + Text.get("world.error"));
            return null;
        }

        return world;
    }

    public List<String> tabComplete(CommandSender sender, String supplied) {
        return Bukkit.getWorlds().stream()
                .map(World::getName)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}
