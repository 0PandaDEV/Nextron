package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand extends CommandBase {

    public TimeCommand() {
        super("day", "Allows you to set the time", "/day | night | midnight | noon", "", "nextron.time");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("day")) {
            player.getLocation().getWorld().setTime(1000);
            player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "day"));
        } else if (label.equalsIgnoreCase("night")) {
            player.getLocation().getWorld().setTime(13000);
            player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "night"));
        } else if (label.equalsIgnoreCase("midnight")) {
            player.getLocation().getWorld().setTime(18000);
            player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "midnight"));
        } else if (label.equalsIgnoreCase("noon")) {
            player.getLocation().getWorld().setTime(6000);
            player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "noon"));
        }
    }

}