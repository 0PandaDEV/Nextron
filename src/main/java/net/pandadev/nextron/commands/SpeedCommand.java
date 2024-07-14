package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.SettingsAPI;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.entity.Player;

@Command(name = "speed")
@Permission("nextron.speed")
public class SpeedCommand extends HelpBase {

    public SpeedCommand() {
        super("speed, Allows you to set your fly/walk speed, /speed [speed]");
    }

    @Execute
    public void speedCommand(@Context Player player, @OptionalArg Integer speed) {
        if (speed == null) {
            if (!player.hasPermission("nextron.speed.reset")) {
                player.sendMessage(Main.getNoPerm());
            }
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed((float) 0.1);
            player.setWalkSpeed((float) 0.2);
            player.sendMessage(Main.getPrefix() + Text.get("speed.reset"));
            return;
        }

        if (NumberUtils.isNumber(String.valueOf(speed))) {
            float parsedSpeed = speed / 10f;
            if (parsedSpeed > 1) {
                player.sendMessage(Main.getPrefix() + Text.get("speed.error"));
            } else {
                if (player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setFlySpeed(parsedSpeed);
                    if (SettingsAPI.allowsFeedback(player)) {
                        player.sendMessage(
                                Main.getPrefix() + Text.get("speed.fly.success").replace("%s", speed.toString()));
                    }
                } else if (player.isOnGround()) {
                    player.setWalkSpeed(parsedSpeed);
                    if (SettingsAPI.allowsFeedback(player)) {
                        player.sendMessage(
                                Main.getPrefix() + Text.get("speed.walk.success").replace("%s", speed.toString()));
                    }
                }
            }
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("speed.error.digit"));
        }
    }
}
