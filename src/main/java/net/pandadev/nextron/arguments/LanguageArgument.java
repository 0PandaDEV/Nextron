package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Language;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageArgument extends ArgumentResolver<CommandSender, Language> {

    @Override
    protected ParseResult<Language> parse(Invocation<CommandSender> invocation, Argument<Language> argument, String s) {
        List<String> languages = getAvailableLanguages();
        if (languages.contains(s.toLowerCase())) {
            return ParseResult.success(new Language(s));
        }
        return ParseResult.failure(Main.getPrefix() + Text.get("language.set.error").replace("%l", String.join(", ", languages)));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Language> argument, SuggestionContext context) {
        List<String> languages = getAvailableLanguages();
        return SuggestionResult.of(languages.stream()
                .filter(language -> language.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase()))
                .collect(Collectors.toList()));
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
