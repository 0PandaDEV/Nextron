package net.pandadev.nextron.commands;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.entity.Player;

public class EnderchestCommand extends HelpBase {
    public EnderchestCommand() {
        super("enderchest, Opens a GUI where the player can access his enderchest., /enderchest [player]\n/ec [player]");
    }


    @Command(names = {"enderchest", "ec"}, permission = "nextron.enderchest", playerOnly = true)
    public void enderchestCommand(Player player, @Param(name = "target", required = false) Player target) {
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
