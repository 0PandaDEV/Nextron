package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "ping")
@Permission("nextron.ping")
public class PingCommand extends HelpBase {

    public PingCommand() {
        super("ping, Returns your current ping ot the server, /ping [player]");
    }

    @Execute
    public void pingCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isPresent()) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("ping.other")
                .replace("%t", target.get().getName())
                .replace("%p", String.valueOf(target.get().getPing())));
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        player.sendMessage(Main.getPrefix() + TextAPI.get("ping").replace("%p", String.valueOf(player.getPing())));
    }
}