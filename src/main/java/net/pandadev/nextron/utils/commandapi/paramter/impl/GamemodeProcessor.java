package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamemodeProcessor extends Processor<GameMode> {
    private static final Map<String, GameMode> aliases = new HashMap<>();

    static {
        aliases.put("0", GameMode.SURVIVAL);
        aliases.put("s", GameMode.SURVIVAL);
        aliases.put("1", GameMode.CREATIVE);
        aliases.put("c", GameMode.CREATIVE);
        aliases.put("2", GameMode.ADVENTURE);
        aliases.put("a", GameMode.ADVENTURE);
        aliases.put("3", GameMode.SPECTATOR);
        aliases.put("sp", GameMode.SPECTATOR);
    }

    @Override
    public GameMode process(CommandSender sender, String supplied) {
        if (aliases.containsKey(supplied.toLowerCase())) {
            return aliases.get(supplied.toLowerCase());
        }

        try {
            return GameMode.valueOf(supplied.toUpperCase());
        } catch (IllegalArgumentException ex) {
            sender.sendMessage("§cYou have entered an invalid game mode.");
            return null;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String supplied) {
        List<String> gameModeNames = Arrays.stream(GameMode.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();

        List<String> aliasList = new ArrayList<>(aliases.keySet());

        return Stream.concat(gameModeNames.stream(), aliasList.stream())
                .filter(name -> name.startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}