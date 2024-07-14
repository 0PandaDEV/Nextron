package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RankArgument extends ArgumentResolver<CommandSender, Rank> {

    @Override
    protected ParseResult<Rank> parse(Invocation<CommandSender> invocation, Argument<Rank> argument, String s) {
        List<String> ranks = getAvailableRanks();
        if (ranks.isEmpty()) {
            return ParseResult.failure();
        }
        if (ranks.contains(s.toLowerCase())) {
            return ParseResult.success(new Rank(s));
        }
        return ParseResult.failure(Main.getPrefix() + Text.get("rank.dontexists"));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Rank> argument, SuggestionContext context) {
        List<String> ranks = getAvailableRanks();
        if (ranks.isEmpty()) {
            return SuggestionResult.of(new ArrayList<>());
        }
        return SuggestionResult.of(ranks.stream()
                .filter(rank -> rank.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase()))
                .collect(Collectors.toList()));
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
