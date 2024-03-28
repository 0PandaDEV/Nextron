package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.HomeInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeProcessor extends Processor<HomeInfo> {

    @Override
    public HomeInfo process(CommandSender sender, String supplied) {
        if (sender instanceof Player player) {
            var section = Configs.home.getConfigurationSection("Homes." + player.getUniqueId());
            if (section != null && section.getKeys(false).contains(supplied.toLowerCase()) && !supplied.equalsIgnoreCase("default")) {
                return new HomeInfo(supplied);
            }
            sender.sendMessage("§cThis Home does not exist");
        }
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        if (sender instanceof Player player) {
            var section = Configs.home.getConfigurationSection("Homes." + player.getUniqueId());
            if (section == null) {
                return new ArrayList<>();
            }
            return section.getKeys(false).stream()
                    .filter(homeName -> homeName.toLowerCase().startsWith(input.toLowerCase()) && !homeName.equalsIgnoreCase("default"))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
