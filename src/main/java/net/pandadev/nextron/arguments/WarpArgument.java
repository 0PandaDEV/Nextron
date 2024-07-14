package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Warp;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WarpArgument extends ArgumentResolver<CommandSender, Warp> {

    @Override
    protected ParseResult<Warp> parse(Invocation<CommandSender> invocation, Argument<Warp> argument, String s) {
        var section = Configs.warp.getConfigurationSection("Warps");
        if (section != null && section.getKeys(false).contains(s.toLowerCase())) {
            return ParseResult.success(new Warp(s));
        }
        return ParseResult.failure(Main.getPrefix() + Text.get("warp.error").replace("%w", s));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        var section = Configs.warp.getConfigurationSection("Warps");
        if (section == null) {
            return SuggestionResult.of(new ArrayList<>());
        }
        return SuggestionResult.of(section.getKeys(false).stream()
                .filter(warpName -> warpName.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase()))
                .collect(Collectors.toList()));
    }
}
