package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.entity.Player;

@Command(name = "invsee")
@Permission("nextron.invsee")
public class InvseeCommand extends HelpBase {

    public InvseeCommand() {
        super("invsee, Lets you inspect and control another player's inventory, /invsee <player>");
    }

    public void invseeCommand(@Context Player player, @Arg Player target) {
        if (target != player) {
            player.openInventory(target.getInventory());
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("invsee.error"));
        }
    }
}
