package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Home;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HomeArgument extends ArgumentResolver<CommandSender, Home> {

    @Override
    protected ParseResult<Home> parse(Invocation<CommandSender> invocation, Argument<Home> argument, String s) {
        if (invocation.sender() instanceof Player player) {
            var section = Configs.home.getConfigurationSection("Homes." + player.getUniqueId());
            if (section != null && section.getKeys(false).contains(s.toLowerCase()) && !s.equalsIgnoreCase("default")) {
                return ParseResult.success(new Home(s.toLowerCase()));
            }
        }
        return ParseResult.failure(Main.getPrefix() + Text.get("home.notfound").replace("%h", s));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Home> argument, SuggestionContext context) {
        if (invocation.sender() instanceof Player player) {
            var section = Configs.home.getConfigurationSection("Homes." + player.getUniqueId());
            if (section == null) {
                return SuggestionResult.of(new ArrayList<>());
            }
            return SuggestionResult.of(section.getKeys(false).stream().filter(homeName -> homeName.toLowerCase().startsWith(context.getCurrent().toString().toLowerCase()) && !homeName.equalsIgnoreCase("default")).collect(Collectors.toList()));
        }
        return SuggestionResult.of(new ArrayList<>());
    }
}
