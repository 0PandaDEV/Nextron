package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "top")
@Permission("nextron.top")
public class TopCommand extends HelpBase {

    public TopCommand() {
        super("top, Teleports you to the highest block above you, /top");
    }

    @Execute
    public void topCommand(@Context Player player) {
        Location location = player.getLocation();
        int highestY = player.getWorld().getHighestBlockYAt(location);
        if (highestY <= location.getBlockY()) {
            player.sendMessage(Main.getPrefix() + Text.get("top.no_blocks_above"));
            return;
        }
        location.setY(highestY + 1.0);
        player.teleport(location);
        if (SettingsAPI.allowsFeedback(player))
            player.sendMessage(Main.getPrefix() + Text.get("top.success"));
    }


}