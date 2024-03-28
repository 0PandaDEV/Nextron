package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Feature;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeatureProcessor extends Processor<Feature> {

    @Override
    public Feature process(CommandSender sender, String supplied) {
        List<String> features = getAvailableRanks();
        if (features.contains(supplied.toLowerCase())) {
            return new Feature(supplied);
        }

        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        List<String> features = getAvailableRanks();
        return features.stream()
                .filter(rank -> rank.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<String> getAvailableRanks() {
        List<String> list = new ArrayList<>();
        list.add("warp_system");
        list.add("home_system");
        list.add("rank_system");
        list.add("tpa_system");
        list.add("join_leave_system");
        return list;
    }

}
