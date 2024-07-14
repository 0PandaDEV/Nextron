package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import net.pandadev.nextron.guis.GUIs;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command(name = "menu", aliases = {"m"})
public class MenuCommand extends HelpBase {

    public MenuCommand() {
        super("menu, Opens the menu where you can simply do everything, /menu\n/m");
    }

    @Execute
    public void menuCommand(@Context Player player) {
        GUIs.mainGui(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
    }
}
