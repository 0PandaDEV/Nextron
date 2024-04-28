package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.Language;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageCommand extends HelpBase {

    public LanguageCommand() {
        super("language, Allows you to change the plugins language, /language <lanugage>");
    }

    @Command(names = {"language"}, permission = "nextron.language")
    public void languageCommand(CommandSender sender, @Param(name = "language") Language language) {
        File[] files = new File(Main.getInstance().getDataFolder(), "/lang").listFiles();
        if (files == null) {
            sender.sendMessage(Main.getPrefix() + "Error: Language directory not found or inaccessible.");
            return;
        }

        List<String> languages = Arrays.stream(files)
                .map(file -> file.getName().replace(".json", ""))
                .collect(Collectors.toList());

        if (!languages.contains(language.getName().toLowerCase())) {
            sender.sendMessage(Main.getPrefix() + Text.get("language.set.error").replace("%l", String.join(", ", languages)));
            return;
        }

        Main.getInstance().getConfig().set("language", language.getName());
        Main.getInstance().saveConfig();
        sender.sendMessage(Main.getPrefix() + Text.get("language.set.success").replace("%l", language.getName()));
    }

}