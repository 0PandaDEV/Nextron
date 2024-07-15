package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.WarpAPI;
import net.pandadev.nextron.arguments.objects.Warp;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class WarpArgument extends ArgumentResolver<CommandSender, Warp> {

    @Override
    protected ParseResult<Warp> parse(Invocation<CommandSender> invocation, Argument<Warp> argument, String s) {
        if (WarpAPI.getWarp(s.toLowerCase()) != null) {
            return ParseResult.success(new Warp(s));
        }
        return ParseResult.failure(Main.getPrefix() + Text.get("warp.error").replace("%w", s));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        List<String> warps = WarpAPI.getWarps();
        return SuggestionResult.of(warps.stream()
                .filter(warpName -> warpName.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase()))
                .collect(Collectors.toList()));
    }
}
