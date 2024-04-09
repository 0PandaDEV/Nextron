package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.entity.Player;

public class TimeCommand extends HelpBase {

    public TimeCommand() {
        super("day", "Allows you to set the time", "/day\n/night\n/midnight\n/noon");
    }


    @Command(names = "day", permission = "nextron.time.day")
    public void dayCommand(Player player) {
        player.getLocation().getWorld().setTime(1000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "day"));
    }

    @Command(names = "night", permission = "nextron.time.night")
    public void nightCommand(Player player) {
        player.getLocation().getWorld().setTime(13000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "night"));
    }

    @Command(names = "midnight", permission = "nextron.time.midnight")
    public void midnightCommand(Player player) {
        player.getLocation().getWorld().setTime(18000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "midnight"));
    }

    @Command(names = "noon", permission = "nextron.time.noon")
    public void noonCommand(Player player) {
        player.getLocation().getWorld().setTime(6000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "noon"));
    }

}