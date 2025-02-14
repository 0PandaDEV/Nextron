package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "back")
@Permission("nextron.back")
public class BackCommand extends HelpBase {

    public BackCommand() {
        super("back, Teleports the player back to the last (death, tpa, home, warp) position., /back [player]");
    }

    @Execute
    void backCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        Player player = (Player) sender;

        if (target.isEmpty()) {
            teleportBack(player);
        } else {
            teleportBack(target.get());
            player.sendMessage(Main.getPrefix() + TextAPI.get("back.other.success").replace("%p", target.get().getName()));
        }
    }

    private void teleportBack(Player player) {
        Location lastPosition = SettingsAPI.getLastPosition(player);
        if (lastPosition != null) {
            SettingsAPI.setBack(player, true);
            player.teleport(lastPosition);
        } else {
            player.sendMessage(Main.getPrefix() + TextAPI.get("back.no_position"));
        }
    }
}