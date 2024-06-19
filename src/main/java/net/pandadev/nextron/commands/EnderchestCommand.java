package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.entity.Player;

@Command(name = "enderchest", aliases = "ec")
@Permission("nextron.enderchest")
public class EnderchestCommand extends HelpBase {

    public EnderchestCommand() {
        super("enderchest, Opens a GUI where the player can access his enderchest., /enderchest [player]\n/ec [player]");
    }

    @Execute
    void enderchestCommand(@Context Player player, @OptionalArg Player target) {
        if (target == null) {
            player.openInventory(player.getEnderChest());
            return;
        }

        if (!player.hasPermission("nextron.enderchest.other")) {
            player.sendMessage(Main.getNoPerm());
            return;
        }

        player.openInventory(target.getEnderChest());
        return;
    }
}
