package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RankProcessor extends Processor<Rank> {

    @Override
    public Rank process(CommandSender sender, String supplied) {
        List<String> ranks = getAvailableRanks();
        if (ranks.isEmpty()) {
            sender.sendMessage("§cNo ranks available.");
            return null;
        }
        if (ranks.contains(supplied.toLowerCase())) {
            return new Rank(supplied);
        }
        sender.sendMessage("§cThis rank does not exist. Available ranks: " + String.join(", ", ranks));
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        List<String> ranks = getAvailableRanks();
        if (ranks.isEmpty()) {
            return new ArrayList<>();
        }
        return ranks.stream()
                .filter(rank -> rank.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<String> getAvailableRanks() {
        ConfigurationSection ranksSection = Main.getInstance().getConfig().getConfigurationSection("Ranks");
        if (ranksSection == null) {
            System.out.println("The 'Ranks' section is not found in the config.");
            return new ArrayList<>();
        }
        return new ArrayList<>(ranksSection.getKeys(false));
    }
}
