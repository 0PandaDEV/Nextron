package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Language;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Command(name = "language")
@Permission("nextron.language")
public class LanguageCommand extends HelpBase {

    public LanguageCommand() {
        super("language, Allows you to change the plugins language, /language <lanugage>");
    }

    @Execute
    public void languageCommand(@Context CommandSender sender, @Arg Language language) {
        File[] files = new File(Main.getInstance().getDataFolder(), "/lang").listFiles();
        if (files == null) {
            sender.sendMessage(Main.getPrefix() + "Error: Language directory not found or inaccessible.");
            return;
        }

        List<String> languages = Arrays.stream(files)
                .map(file -> file.getName().replace(".json", ""))
                .collect(Collectors.toList());

        if (!languages.contains(language.getName().toLowerCase())) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("language.set.error").replace("%l", String.join(", ", languages)));
            return;
        }

        Main.getInstance().getConfig().set("language", language.getName());
        Main.getInstance().saveConfig();
        sender.sendMessage(Main.getPrefix() + TextAPI.get("language.set.success").replace("%l", language.getName()));
    }

}