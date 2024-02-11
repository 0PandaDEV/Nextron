package net.pandadev.nextron.commands;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.GUIs;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand extends CommandBase implements CommandExecutor {

    public MenuCommand() {
        super("menu", "Opens the menu where you can simply do everything", "/menu", "/m", "");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("menu") || label.equalsIgnoreCase("m") && args.length == 0) {
            GUIs.mainGui(player);
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
        } else {
            player.sendMessage(Main.getPrefix() + "§c/menu");
        }
    }

}
