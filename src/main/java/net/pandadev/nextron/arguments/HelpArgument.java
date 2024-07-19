package net.pandadev.nextron.arguments;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Help;
import net.pandadev.nextron.commands.HelpBase;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

public class HelpArgument extends ArgumentResolver<CommandSender, Help> {
    @Override
    protected ParseResult<Help> parse(Invocation<CommandSender> invocation, Argument<Help> argument, String s) {
        if (HelpBase.commands.containsKey(s)) {
            return ParseResult.success(new Help(s, HelpBase.getName(s), HelpBase.getDescription(s), HelpBase.getUsage(s)));
        }

        return ParseResult.failure(Main.getPrefix() + TextAPI.get("help.command.error"));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Help> argument, SuggestionContext context) {
        return SuggestionResult.of(HelpBase.commands.keySet().stream().filter(cmd -> cmd.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase())).collect(Collectors.toList()));
    }
}
