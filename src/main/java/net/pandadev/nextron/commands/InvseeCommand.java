package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.entity.Player;

@Command(name = "invsee")
@Permission("nextron.invsee")
public class InvseeCommand extends HelpBase {

    public InvseeCommand() {
        super("invsee, Lets you inspect and control another player's inventory, /invsee <player>");
    }

    @Execute
    public void invseeCommand(@Context Player player, @Arg Player target) {
        if (target != player) {
            player.openInventory(target.getInventory());
        } else {
            player.sendMessage(Main.getPrefix() + TextAPI.get("invsee.error"));
        }
    }
}
