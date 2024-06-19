package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.entity.Player;

@RootCommand
public class TimeCommand extends HelpBase {

    public TimeCommand() {
        super("day, Sets the time to day (1000), /day",
                "night, Sets the time to night (13000), /night",
                "midnight, Sets the time to midnight (18000), /midnight",
                "noon, Sets the time to noon (6000), /noon");
    }


    @Execute(name = "day")
    @Permission("nextron.day")
    public void dayCommand(Player player) {
        player.getLocation().getWorld().setTime(1000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "day"));
    }

    @Execute(name = "night")
    @Permission("nextron.night")
    public void nightCommand(Player player) {
        player.getLocation().getWorld().setTime(13000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "night"));
    }

    @Execute(name = "midnight")
    @Permission("nextron.midnight")
    public void midnightCommand(Player player) {
        player.getLocation().getWorld().setTime(18000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "midnight"));
    }

    @Execute(name = "noon")
    @Permission("nextron.noon")
    public void noonCommand(Player player) {
        player.getLocation().getWorld().setTime(6000);
        player.sendMessage(Main.getPrefix() + Text.get("time.success").replace("%d", "noon"));
    }

}