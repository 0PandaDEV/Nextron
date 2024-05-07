package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand extends HelpBase {

    public PingCommand() {
        super("ping, Returns your current ping ot the server, /ping [player]");
    }

    @Command(names = {"ping"}, permission = "nextron.ping")
    public void pingCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
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