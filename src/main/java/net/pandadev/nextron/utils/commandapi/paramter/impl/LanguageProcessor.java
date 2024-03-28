package net.pandadev.nextron.utils.commandapi.paramter.impl;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.paramter.Processor;
import net.pandadev.nextron.utils.commandapi.processors.Language;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageProcessor extends Processor<Language> {

    @Override
    public Language process(CommandSender sender, String supplied) {
        List<String> languages = getAvailableLanguages();
        if (languages.contains(supplied.toLowerCase())) {
            return new Language(supplied);
        }
        sender.sendMessage("§cThis language does not exist. Available languages: " + String.join(", ", languages));
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String input) {
        List<String> languages = getAvailableLanguages();
        return languages.stream()
                .filter(language -> language.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<String> getAvailableLanguages() {
        File dataFolder = new File(Main.getInstance().getDataFolder(), "/lang");
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println("Language directory not found or is not a directory.");
            return List.of();
        }
        File[] files = dataFolder.listFiles();
        if (files == null) {
            System.out.println("Error listing language files.");
            return List.of();
        }
        return Arrays.stream(files)
                .filter(File::isFile)
                .map(file -> file.getName().replace(".json", "").toLowerCase())
                .collect(Collectors.toList());
    }

}
