package net.pandadev.nextron.arguments;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Seed;
import org.bukkit.command.CommandSender;

public class SeedArgument extends ArgumentResolver<CommandSender, Seed> {

    @Override
    protected ParseResult<Seed> parse(Invocation<CommandSender> invocation, Argument<Seed> argument, String s) {
        long seed;
        try {
            seed = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return ParseResult.failure(Main.getPrefix() + Text.get("world.create.seed.error"));
        }
        return ParseResult.success(new Seed(seed));
    }
}
