package net.pandadev.nextron.commands;

import net.pandadev.nextron.guis.GUIs;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MenuCommand extends HelpBase {

    public MenuCommand() {
        super("menu, Opens the menu where you can simply do everything, /menu\n/m");
    }

    @Command(names = {"menu", "m"})
    public void menuCommand(Player player) {
        GUIs.mainGui(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
    }
}
