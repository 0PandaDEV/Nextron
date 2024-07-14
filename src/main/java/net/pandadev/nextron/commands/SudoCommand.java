package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "sudo")
@Permission("nextron.sudo")
public class SudoCommand extends HelpBase {

    public SudoCommand() {
        super("sudo, Forces a player to execute a command, /sudo <player> <command>");
    }

    @Execute
    public void sudoCommand(@Context CommandSender sender, @Arg Player target, @Join String command) {
        target.chat("/" + command);

        sender.sendMessage(Main.getPrefix()
                + Text.get("sudo.success").replace("%t", target.getName()).replace("%b", command));
    }
}
