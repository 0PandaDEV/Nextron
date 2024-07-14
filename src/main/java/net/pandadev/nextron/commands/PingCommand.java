package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "ping")
@Permission("nextron.ping")
public class PingCommand extends HelpBase {

    public PingCommand() {
        super("ping, Returns your current ping ot the server, /ping [player]");
    }

    @Execute
    public void pingCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target != null) {
            sender.sendMessage(Main.getPrefix() + Text.get("ping.other").replace("%t", target.getName()).replace("%p", String.valueOf(target.getPing())));
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        player.sendMessage(Main.getPrefix() + Text.get("ping").replace("%p", String.valueOf(player.getPing())));
    }
}