package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TopCommand extends HelpBase {

    public TopCommand() {
        super("top, Teleports you to the highest block above you, /top");
    }

    @Command(names = {"top"}, permission = "nextron.top", playerOnly = true)
    public void topCommand(Player player) {
        Location location = player.getLocation();
        int highestY = player.getWorld().getHighestBlockYAt(location);
        if (highestY <= location.getBlockY()) {
            player.sendMessage(Main.getPrefix() + Text.get("top.no_blocks_above"));
            return;
        }
        location.setY(highestY + 1.0);
        player.teleport(location);
        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
            player.sendMessage(Main.getPrefix() + Text.get("top.success"));
    }


}