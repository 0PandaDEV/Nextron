package net.pandadev.nextron.utils.commandapi.paramter.impl;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Warp;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpProcessor extends Processor<Warp> {

    @Override
    public Warp process(CommandSender sender, String supplied) {
        var section = Configs.warp.getConfigurationSection("Warps");
        if (section != null && section.getKeys(false).contains(supplied.toLowerCase())) {
            return new Warp(supplied);
        }
        sender.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", supplied));
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        var section = Configs.warp.getConfigurationSection("Warps");
        if (section == null) {
            return new ArrayList<>();
        }
        return section.getKeys(false).stream()
                .filter(warpName -> warpName.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}
