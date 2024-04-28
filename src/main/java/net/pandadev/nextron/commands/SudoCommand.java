package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand extends HelpBase {

    public SudoCommand() {
        super("sudo, Forces a player to execute a command, /sudo <player> <command>");
    }

    @Command(names = "sudo", permission = "nextron.sudo")
    public void sudoCommand(CommandSender sender, @Param(name = "target") Player target, @Param(name = "command", concated = true) String command) {
        target.chat("/" + command);

        sender.sendMessage(Main.getPrefix()
                + Text.get("sudo.success").replace("%t", target.getName()).replace("%b", command));
    }
}
