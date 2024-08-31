package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "back")
@Permission("nextron.back")
public class BackCommand extends HelpBase {

    public BackCommand() {
        super("back, Teleports the player back to the last (death, tpa, home, warp) position., /back [player]");
    }

    @Execute
    void backCommand(@Context CommandSender sender, @OptionalArg Player target) {
        Player player = (Player) sender;

        if (target == null) {
            teleportBack(player);
        } else {
            teleportBack(target);
            player.sendMessage(Main.getPrefix() + TextAPI.get("back.other.success").replace("%p", target.getName()));
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