package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.entity.Player;

public class InvseeCommand extends HelpBase {

    public InvseeCommand() {
        super("invsee", "Lets you inspect and control another player's inventory", "/invsee <player>");
    }

    @Command(names = {"invsee"}, permission = "nextron.invsee")
    public void invseeCommand(Player player, @Param(name = "target") Player target) {
        if (target != player) {
            player.openInventory(target.getInventory());
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("invsee.error"));
        }
    }
}
