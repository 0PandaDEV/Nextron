package net.pandadev.nextron.commands;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.entity.Player;

public class PingCommand extends HelpBase {

    public PingCommand() {
        super("ping, Returns your current ping ot the server, /ping");
    }

    @Command(names = {"ping"}, permission = "nextron.ping", playerOnly = true)
    public void pingCommand(Player player) {
        player.sendMessage(Main.getPrefix() + "§7Your ping is §a" + player.getPing() + "ms");
    }
}