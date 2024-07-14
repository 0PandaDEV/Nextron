package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Feature;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class FeatureArgument extends ArgumentResolver<CommandSender, Feature> {

    private List<String> getAvailableRanks() {
        List<String> list = new ArrayList<>();
        list.add("warp_system");
        list.add("home_system");
        list.add("rank_system");
        list.add("tpa_system");
        list.add("join_leave_system");
        return list;
    }

    @Override
    protected ParseResult<Feature> parse(Invocation<CommandSender> invocation, Argument<Feature> context, String supplied) {
        List<String> features = getAvailableRanks();
        if (features.contains(supplied.toLowerCase())) {
            return ParseResult.success(new Feature(supplied));
        }

        return ParseResult.failure(Main.getPrefix() + Text.get("feature.validvalues"));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Feature> argument, SuggestionContext context) {
        return SuggestionResult.of(getAvailableRanks());
    }


}
