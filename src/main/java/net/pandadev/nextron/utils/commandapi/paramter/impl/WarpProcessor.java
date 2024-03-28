package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpProcessor extends Processor<Warp> {

    @Override
    public Warp process(CommandSender sender, String supplied) {
        if (sender instanceof Player player) {
            var section = Configs.warp.getConfigurationSection("Warps");
            if (section != null && section.getKeys(false).contains(supplied.toLowerCase())) {
                return new Warp(supplied);
            }
            sender.sendMessage("§cThis Home does not exist");
        }
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
